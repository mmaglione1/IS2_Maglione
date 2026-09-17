package com.colegio.controllers;

import com.colegio.dtos.AlumnoDTO;
import com.colegio.services.AlumnoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/alumnos")
public class AlumnoController {

    @Autowired
    private AlumnoService alumnoService;

    /**
     * Muestra el listado de alumnos con su grado y aula asignada.
     */
    @GetMapping
    public String listarAlumnos(Model model) {
        model.addAttribute("alumnos", alumnoService.listarTodos());
        return "alumnos/lista";
    }

    /**
     * Muestra el formulario para registrar un nuevo alumno.
     */
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevoAlumno(Model model) {
        if (!model.containsAttribute("alumnoDTO")) {
            model.addAttribute("alumnoDTO", new AlumnoDTO());
        }
        // Carga la lista de Aulas/Grados disponibles para el selector <select>
        model.addAttribute("aulas", alumnoService.listarAulas());
        return "alumnos/form";
    }

    /**
     * Procesa el formulario de matriculación escolar.
     */
    @PostMapping("/guardar")
    public String guardarAlumno(@Valid @ModelAttribute("alumnoDTO") AlumnoDTO alumnoDTO,
                                BindingResult result,
                                Model model,
                                RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("aulas", alumnoService.listarAulas());
            return "alumnos/form";
        }

        try {
            alumnoService.registrarAlumno(alumnoDTO);
            redirectAttributes.addFlashAttribute("exito", "Alumno registrado correctamente con su grado y aula.");
            return "redirect:/alumnos";

        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("aulas", alumnoService.listarAulas());
            return "alumnos/form";
        }
    }
}