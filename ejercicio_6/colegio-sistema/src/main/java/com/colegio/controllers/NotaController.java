package com.colegio.controllers;

import com.colegio.dtos.NotaDTO;
import com.colegio.services.AlumnoService;
import com.colegio.services.NotaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * ============================================================================
 * CONTROLADOR MVC: CALIFICACIONES POR MATERIA Y BOLETINES
 * ============================================================================
 */
@Controller
@RequestMapping("/notas")
public class NotaController {

    @Autowired
    private NotaService notaService;

    @Autowired
    private AlumnoService alumnoService;

    /**
     * Muestra el formulario para cargar una nueva calificación.
     */
    @GetMapping("/cargar")
    public String mostrarFormularioCarga(Model model) {
        if (!model.containsAttribute("notaDTO")) {
            model.addAttribute("notaDTO", new NotaDTO());
        }
        model.addAttribute("alumnos", alumnoService.listarTodos());
        model.addAttribute("materias", notaService.listarMaterias());
        return "notas/carga";
    }

    /**
     * Procesa la calificación ingresada por el docente.
     */
    @PostMapping("/guardar")
    public String guardarNota(@Valid @ModelAttribute("notaDTO") NotaDTO notaDTO,
                              BindingResult result,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("alumnos", alumnoService.listarTodos());
            model.addAttribute("materias", notaService.listarMaterias());
            return "notas/carga";
        }

        try {
            notaService.registrarNota(notaDTO);
            redirectAttributes.addFlashAttribute("exito", "Calificación registrada con éxito.");
            return "redirect:/notas/boletin/" + notaDTO.getAlumnoId();

        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("alumnos", alumnoService.listarTodos());
            model.addAttribute("materias", notaService.listarMaterias());
            return "notas/carga";
        }
    }

    /**
     * Muestra el boletín de calificaciones de un alumno en todas sus materias.
     */
    @GetMapping("/boletin/{alumnoId}")
    public String verBoletin(@PathVariable("alumnoId") Long alumnoId, Model model) {
        try {
            model.addAttribute("alumno", alumnoService.buscarPorId(alumnoId));
            model.addAttribute("notas", notaService.obtenerBoletinPorAlumno(alumnoId));
            return "notas/boletin";

        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/alumnos";
        }
    }
}