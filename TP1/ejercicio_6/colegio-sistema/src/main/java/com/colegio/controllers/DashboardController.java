package com.colegio.controllers;

import com.colegio.dtos.DocenteDTO;
import com.colegio.services.AlumnoService;
import com.colegio.services.DocenteService;
import com.colegio.services.NotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * ============================================================================
 * CONTROLADOR MVC: PANEL PRINCIPAL (DASHBOARD ESCOLAR)
 * ============================================================================
 */
@Controller
public class DashboardController {

    @Autowired
    private DocenteService docenteService;

    @Autowired
    private AlumnoService alumnoService;

    @Autowired
    private NotaService notaService;

    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication authentication) {
        try {
            String correo = authentication.getName();
            DocenteDTO docenteLogueado = docenteService.obtenerPorCorreo(correo);

            // Inyección de información en el Model para renderizar en Thymeleaf
            model.addAttribute("docente", docenteLogueado);
            model.addAttribute("totalAlumnos", alumnoService.listarTodos().size());
            model.addAttribute("totalAulas", alumnoService.listarAulas().size());
            model.addAttribute("totalMaterias", notaService.listarMaterias().size());

            return "dashboard";

        } catch (Exception e) {
            model.addAttribute("error", "Error al recuperar datos del panel: " + e.getMessage());
            return "login";
        }
    }
}