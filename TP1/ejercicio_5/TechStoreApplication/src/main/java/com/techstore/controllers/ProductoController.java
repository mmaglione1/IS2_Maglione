package com.techstore.controllers;

import com.techstore.dtos.ProductoDTO;
import com.techstore.entities.Usuario;
import com.techstore.services.ProductoService;
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

import java.util.List;

/**
 * ============================================================================
 * CONTROLADOR MVC: CATÁLOGO Y STOCK DE PRODUCTOS TECNOLÓGICOS
 * ============================================================================
 */
@Controller
@RequestMapping("/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    /**
     * Muestra la tabla con el catálogo de productos y su stock actualizado.
     */
    @GetMapping
    public String listarProductos(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return "redirect:/login";
        }

        List<ProductoDTO> productos = productoService.listarProductosActivos();
        model.addAttribute("productos", productos);
        model.addAttribute("usuario", usuario);

        if (!model.containsAttribute("nuevoProducto")) {
            model.addAttribute("nuevoProducto", new ProductoDTO());
        }

        return "productos";
    }

    /**
     * Registra un nuevo artículo tecnológico en el catálogo.
     */
    @PostMapping("/guardar")
    public String guardarProducto(@Valid @ModelAttribute("nuevoProducto") ProductoDTO productoDTO,
                                  BindingResult result,
                                  HttpSession session,
                                  RedirectAttributes redirectAttributes) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return "redirect:/login";
        }

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.nuevoProducto", result);
            redirectAttributes.addFlashAttribute("nuevoProducto", productoDTO);
            redirectAttributes.addFlashAttribute("errorModal", "Revise los campos del formulario.");
            return "redirect:/productos";
        }

        try {
            productoService.crearProducto(productoDTO);
            redirectAttributes.addFlashAttribute("exito", "Producto agregado exitosamente al catálogo.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/productos";
    }
}
