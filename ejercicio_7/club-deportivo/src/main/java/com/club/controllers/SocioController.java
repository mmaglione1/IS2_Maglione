package com.club.controllers;

import com.club.dtos.FamiliarDTO;
import com.club.dtos.SocioRequestDTO;
import com.club.services.ClubService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/socios")
@RequiredArgsConstructor
public class SocioController {

    private final ClubService clubService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("socios", clubService.listarSocios());
        return "socios/lista";
    }

    @GetMapping("/nuevo")
    public String formNuevo(Model model) {
        if (!model.containsAttribute("socioDTO")) {
            model.addAttribute("socioDTO", new SocioRequestDTO());
        }
        return "socios/form";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("socioDTO") SocioRequestDTO dto,
                          BindingResult result,
                          RedirectAttributes redirect) {
        if (result.hasErrors()) {
            redirect.addFlashAttribute("org.springframework.validation.BindingResult.socioDTO", result);
            redirect.addFlashAttribute("socioDTO", dto);
            return "redirect:/socios/nuevo";
        }
        try {
            clubService.registrarSocio(dto);
            redirect.addFlashAttribute("exito", "Socio titular registrado exitosamente.");
        } catch (Exception e) {
            redirect.addFlashAttribute("error", e.getMessage());
            return "redirect:/socios/nuevo";
        }
        return "redirect:/socios";
    }

    @PostMapping("/familiar/guardar")
    public String guardarFamiliar(@Valid @ModelAttribute FamiliarDTO dto,
                                  RedirectAttributes redirect) {
        try {
            clubService.agregarFamiliar(dto);
            redirect.addFlashAttribute("exito", "Familiar añadido correctamente.");
        } catch (Exception e) {
            redirect.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/socios";
    }
}