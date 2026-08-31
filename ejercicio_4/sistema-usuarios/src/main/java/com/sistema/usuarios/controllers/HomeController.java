package com.sistema.usuarios.controllers;

import com.sistema.usuarios.entities.Usuario;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * ============================================================================
 * CAPA DE CONTROLADOR - VISTA PRINCIPAL (Home / Dashboard)
 * ============================================================================
 * @Controller: Gestiona el acceso al panel protegido tras el login exitoso.
 */
@Controller
public class HomeController {

    /**
     * ========================================================================
     * VISTA DE INICIO / DASHBOARD DEL USUARIO
     * ========================================================================
     * Mapea la petición GET /inicio.
     *
     * Regla de Negocio:
     * - Si no hay un objeto "usuarioLogueado" en la sesión HTTP, se impide el
     *   acceso y se redirige inmediatamente a /login.
     * - Si la sesión está activa, inyecta el usuario en el modelo para renderizar
     *   su información personal (Nombre, Apellido, Documento, etc.) en inicio.html.
     *
     * @param session Sesión HTTP actual.
     * @param model Contenedor de atributos para la vista Thymeleaf.
     * @return Nombre de la vista "inicio" o redirección a "redirect:/login".
     */
    @GetMapping("/inicio")
    public String mostrarInicio(HttpSession session, Model model) {
        // Validación de sesión activa
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario == null) {
            // Usuario no autenticado intentando acceder por URL directa
            return "redirect:/login";
        }

        // Pasa los datos del usuario autenticado a la vista inicio.html
        model.addAttribute("usuario", usuario);
        return "inicio";
    }
}