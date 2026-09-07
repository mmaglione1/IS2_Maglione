package com.club.controllers;

import com.club.dtos.PagoCuotaRequestDTO;
import com.club.entities.MedioPago;
import com.club.services.ClubService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.time.LocalDate;

@Controller
@RequestMapping("/pagos")
@RequiredArgsConstructor
public class PagoController {

    private final ClubService clubService;

    @GetMapping
    public String historialPagos(Model model) {
        model.addAttribute("pagos", clubService.listarPagos());
        return "pagos/historial";
    }

    @GetMapping("/nuevo")
    public String nuevoPagoForm(Model model) {
        if (!model.containsAttribute("pagoDTO")) {
            LocalDate hoy = LocalDate.now();
            PagoCuotaRequestDTO dto = PagoCuotaRequestDTO.builder()
                    .mes(hoy.getMonthValue())
                    .anio(hoy.getYear())
                    .build();
            model.addAttribute("pagoDTO", dto);
        }
        model.addAttribute("socios", clubService.listarSocios());
        model.addAttribute("mediosPago", MedioPago.values());
        return "pagos/form";
    }

    @PostMapping("/guardar")
    public String guardarPago(@Valid @ModelAttribute("pagoDTO") PagoCuotaRequestDTO dto,
                              BindingResult result,
                              RedirectAttributes redirect) {
        if (result.hasErrors()) {
            redirect.addFlashAttribute("org.springframework.validation.BindingResult.pagoDTO", result);
            redirect.addFlashAttribute("pagoDTO", dto);
            return "redirect:/pagos/nuevo";
        }
        try {
            clubService.registrarPago(dto);
            redirect.addFlashAttribute("exito", "Pago registrado correctamente para la familia.");
        } catch (Exception e) {
            redirect.addFlashAttribute("error", e.getMessage());
            return "redirect:/pagos/nuevo";
        }
        return "redirect:/pagos";
    }
}