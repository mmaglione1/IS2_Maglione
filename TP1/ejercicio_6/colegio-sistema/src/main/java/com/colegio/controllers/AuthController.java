package com.colegio.controllers;

import com.colegio.dtos.CambioPasswordDTO;
import com.colegio.dtos.RegistroDocenteDTO;
import com.colegio.services.DocenteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * ============================================================================
 * CONTROLADOR MVC: AUTENTICACIÓN, REGISTRO Y CAMBIO DE CLAVE
 * ============================================================================
 * Capa: Controlador (Web Layer - Spring MVC).
 *
 * Anotaciones:
 * - @Controller: Maneja las peticiones web y retorna el nombre de las vistas Thymeleaf.
 * - @Valid: Dispara las validaciones Jakarta de los DTOs.
 * - @ModelAttribute: Enlaza los datos del formulario HTML al DTO correspondiente.
 */
@Controller
public class AuthController {

    @Autowired
    private DocenteService docenteService;

    /**
     * Redirección de la raíz del sitio escolar.
     */
    @GetMapping("/")
    public String index(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/dashboard";
        }
        return "redirect:/login";
    }

    /**
     * Muestra la pantalla de inicio de sesión.
     */
    @GetMapping("/login")
    public String mostrarLogin(@RequestParam(value = "error", required = false) String error,
                               @RequestParam(value = "logout", required = false) String logout,
                               Model model,
                               Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/dashboard";
        }
        if (error != null) {
            model.addAttribute("error", "Correo o contraseña incorrectos. Verifique sus datos.");
        }
        if (logout != null) {
            model.addAttribute("info", "Ha cerrado sesión correctamente.");
        }
        return "login";
    }

    /**
     * Muestra el formulario de registro para nuevos docentes.
     */
    @GetMapping("/registro-docente")
    public String mostrarRegistroDocente(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/dashboard";
        }
        if (!model.containsAttribute("registroDocenteDTO")) {
            model.addAttribute("registroDocenteDTO", new RegistroDocenteDTO());
        }
        return "registro-docente";
    }

    /**
     * Procesa el formulario de registro del docente.
     * Al registrarse con éxito, el Service enviará el correo de bienvenida por Mailtrap.
     */
    @PostMapping("/registro-docente")
    public String procesarRegistroDocente(@Valid @ModelAttribute("registroDocenteDTO") RegistroDocenteDTO dto,
                                          BindingResult result,
                                          Model model,
                                          RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "registro-docente";
        }

        try {
            docenteService.registrarDocente(dto);
            redirectAttributes.addFlashAttribute("exito",
                    "¡Registro completado exitosamente! Se ha enviado un correo de bienvenida a " + dto.getCorreoPersonal());
            return "redirect:/login";

        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "registro-docente";
        }
    }

    /**
     * Muestra la pantalla de cambio de contraseña para el docente autenticado.
     */
    @GetMapping("/cambiar-password")
    public String mostrarCambioPassword(Model model) {
        if (!model.containsAttribute("cambioPasswordDTO")) {
            model.addAttribute("cambioPasswordDTO", new CambioPasswordDTO());
        }
        return "cambiar-password";
    }

    /**
     * Procesa la modificación de la contraseña.
     * Hibernate Envers registrará la auditoría de la actualización en MySQL.
     */
    @PostMapping("/cambiar-password")
    public String procesarCambioPassword(@Valid @ModelAttribute("cambioPasswordDTO") CambioPasswordDTO dto,
                                         BindingResult result,
                                         Authentication authentication,
                                         Model model,
                                         RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "cambiar-password";
        }

        try {
            String correoDocente = authentication.getName();
            docenteService.cambiarPassword(correoDocente, dto);
            redirectAttributes.addFlashAttribute("exito", "Contraseña modificada correctamente.");
            return "redirect:/dashboard";

        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "cambiar-password";
        }
    }
}