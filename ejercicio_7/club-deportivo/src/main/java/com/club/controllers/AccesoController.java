package com.club.controllers;

import com.club.dtos.AccesoRequestDTO;
import com.club.services.ClubService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/accesos")
@RequiredArgsConstructor
public class AccesoController {

    private final ClubService clubService;

    @GetMapping
    public String controlAccesoView(Model model) {
        if (!model.containsAttribute("accesoRequestDTO")) {
            model.addAttribute("accesoRequestDTO", new AccesoRequestDTO());
        }
        model.addAttribute("accesosRecientes", clubService.ultimosAccesos());
        return "accesos/control";
    }

    @PostMapping("/registrar")
    public String registrarAcceso(@Valid @ModelAttribute("accesoRequestDTO") AccesoRequestDTO dto,
                                  BindingResult result,
                                  RedirectAttributes redirect) {
        if (result.hasErrors()) {
            redirect.addFlashAttribute("org.springframework.validation.BindingResult.accesoRequestDTO", result);
            redirect.addFlashAttribute("accesoRequestDTO", dto);
            return "redirect:/accesos";
        }
        try {
            clubService.registrarAcceso(dto);
            redirect.addFlashAttribute("exito", "Movimiento de " + dto.getTipoAcceso() + " registrado con éxito.");
        } catch (IllegalArgumentException e) {
            redirect.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/accesos";
    }
}