package com.techstore.controllers;

import com.techstore.dtos.LoginDTO;
import com.techstore.dtos.RegistroUsuarioDTO;
import com.techstore.entities.Usuario;
import com.techstore.services.UsuarioService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * ============================================================================
 * CONTROLADOR MVC: AUTENTICACIÓN Y SEGURIDAD
 * ============================================================================
 * @Controller: Declara la clase como componente controlador de Spring MVC.
 * Gestiona el ciclo de vida de la sesión HTTP y el acceso a los formularios.
 */
@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioService usuarioService;

    /**
     * Redirección de la raíz del sitio: si hay sesión activa va al inventario,
     * de lo contrario redirige al login.
     */
    @GetMapping("/")
    public String index(HttpSession session) {
        if (session.getAttribute("usuarioLogueado") != null) {
            return "redirect:/productos";
        }
        return "redirect:/login";
    }

    /**
     * Muestra la vista del formulario de login.
     */
    @GetMapping("/login")
    public String mostrarLogin(Model model, HttpSession session) {
        if (session.getAttribute("usuarioLogueado") != null) {
            return "redirect:/productos";
        }
        if (!model.containsAttribute("loginDTO")) {
            model.addAttribute("loginDTO", new LoginDTO());
        }
        return "login";
    }

    /**
     * Procesa las credenciales ingresadas en el formulario de inicio de sesión.
     */
    @PostMapping("/login")
    public String procesarLogin(@Valid @ModelAttribute("loginDTO") LoginDTO loginDTO,
                                BindingResult result,
                                HttpSession session,
                                Model model) {
        if (result.hasErrors()) {
            return "login";
        }

        try {
            // Llama a la capa de servicio para verificar credenciales
            Usuario usuario = usuarioService.autenticar(loginDTO);

            // Guarda el usuario autenticado en la sesión HTTP
            session.setAttribute("usuarioLogueado", usuario);
            return "redirect:/productos";

        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "login";
        }
    }

    /**
     * Muestra la vista del formulario de registro de nuevos usuarios.
     */
    @GetMapping("/registro")
    public String mostrarRegistro(Model model, HttpSession session) {
        if (session.getAttribute("usuarioLogueado") != null) {
            return "redirect:/productos";
        }
        if (!model.containsAttribute("registroDTO")) {
            model.addAttribute("registroDTO", new RegistroUsuarioDTO());
        }
        return "registro";
    }

    /**
     * Procesa el alta de un nuevo operador en el sistema.
     */
    @PostMapping("/registro")
    public String procesarRegistro(@Valid @ModelAttribute("registroDTO") RegistroUsuarioDTO registroDTO,
                                   BindingResult result,
                                   Model model,
                                   RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "registro";
        }

        try {
            usuarioService.registrarUsuario(registroDTO);
            redirectAttributes.addFlashAttribute("exito", "Usuario registrado exitosamente. Ya puede iniciar sesión.");
            return "redirect:/login";

        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "registro";
        }
    }

    /**
     * Cierra la sesión activa invalidando el objeto HttpSession.
     */
    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        session.invalidate();
        redirectAttributes.addFlashAttribute("info", "Ha cerrado sesión correctamente.");
        return "redirect:/login";
    }
}
