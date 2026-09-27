package com.techstore.controllers;

import com.techstore.dtos.DetalleOrdenDTO;
import com.techstore.dtos.OrdenCompraDTO;
import com.techstore.entities.Usuario;
import com.techstore.services.OrdenCompraService;
import com.techstore.services.ProductoService;
import com.techstore.services.ProveedorService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

/**
 * ============================================================================
 * CONTROLADOR MVC: ÓRDENES DE COMPRA A PROVEEDORES MAYORISTAS
 * ============================================================================
 */
@Controller
@RequestMapping("/ordenes")
@RequiredArgsConstructor
public class OrdenCompraController {

    private final OrdenCompraService ordenCompraService;
    private final ProveedorService proveedorService;
    private final ProductoService productoService;

    /**
     * Muestra el historial completo de órdenes de compra emitidas.
     */
    @GetMapping
    public String listarOrdenes(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return "redirect:/login";
        }

        model.addAttribute("ordenes", ordenCompraService.listarTodas());
        model.addAttribute("usuario", usuario);
        return "ordenes";
    }

    /**
     * Carga el formulario para emitir una nueva orden de compra.
     */
    @GetMapping("/nueva")
    public String mostrarFormularioNuevaOrden(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return "redirect:/login";
        }

        if (!model.containsAttribute("ordenCompraDTO")) {
            OrdenCompraDTO dto = new OrdenCompraDTO();
            // Inicializa un primer renglón de detalle para el formulario
            dto.getDetalles().add(new DetalleOrdenDTO());
            model.addAttribute("ordenCompraDTO", dto);
        }

        model.addAttribute("proveedores", proveedorService.listarTodos());
        model.addAttribute("productos", productoService.listarProductosActivos());
        model.addAttribute("usuario", usuario);
        return "nueva-orden";
    }

    /**
     * Procesa el formulario de la orden, ejecuta el cálculo y actualiza el stock en la base de datos.
     */
    @PostMapping("/guardar")
    public String registrarOrden(@Valid @ModelAttribute("ordenCompraDTO") OrdenCompraDTO ordenCompraDTO,
                                 BindingResult result,
                                 HttpSession session,
                                 Model model,
                                 RedirectAttributes redirectAttributes) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return "redirect:/login";
        }

        if (result.hasErrors()) {
            model.addAttribute("proveedores", proveedorService.listarTodos());
            model.addAttribute("productos", productoService.listarProductosActivos());
            model.addAttribute("usuario", usuario);
            return "nueva-orden";
        }

        try {
            // Se envía el DTO a la capa de Servicio para la transacción atómica
            ordenCompraService.registrarOrdenCompra(ordenCompraDTO);
            redirectAttributes.addFlashAttribute("exito", "Orden de compra registrada con éxito. El stock fue actualizado.");
            return "redirect:/productos";

        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("proveedores", proveedorService.listarTodos());
            model.addAttribute("productos", productoService.listarProductosActivos());
            model.addAttribute("usuario", usuario);
            return "nueva-orden";
        }
    }
}