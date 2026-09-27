package com.sistema.usuarios.controllers;

import com.sistema.usuarios.entities.Usuario;
import com.sistema.usuarios.services.UsuarioService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
 * CAPA DE CONTROLADOR (Controller Layer - Spring MVC)
 * ============================================================================
 * @Controller: Marca la clase como un controlador web de Spring MVC que retorna
 * nombres lógicos de vistas HTML para ser renderizadas por Thymeleaf.
 *
 * Gestiona el ciclo de vida de la autenticación, el registro de usuarios
 * y el almacenamiento del usuario autenticado en la sesión HTTP (HttpSession).
 */
@Controller
public class AuthController {

    // Inyección de dependencias de la Capa de Negocio
    @Autowired
    private UsuarioService usuarioService;

    /**
     * ========================================================================
     * VISTA DE INICIO / LANDING O REDIRECCIÓN AL LOGIN
     * ========================================================================
     * Si el usuario ya está autenticado en sesión, lo redirige al panel de inicio;
     * de lo contrario, muestra la vista de login.
     */
    @GetMapping("/")
    public String index(HttpSession session) {
        if (session.getAttribute("usuarioLogueado") != null) {
            return "redirect:/inicio";
        }
        return "redirect:/login";
    }

    /**
     * ========================================================================
     * VISTA DE INICIO DE SESIÓN (LOGIN)
     * ========================================================================
     * Mapea la petición GET /login. Renderiza el formulario con Bootstrap 5
     * y muestra mensajes de error o éxito si existen.
     */
    @GetMapping("/login")
    public String mostrarLogin(HttpSession session, Model model) {
        // Si ya hay sesión activa, ir directo al inicio
        if (session.getAttribute("usuarioLogueado") != null) {
            return "redirect:/inicio";
        }
        return "login";
    }

    /**
     * ========================================================================
     * PROCESAMIENTO DEL LOGIN (AUTENTICACIÓN)
     * ========================================================================
     * Mapea la petición POST /login enviada desde el formulario de login.
     * Delega la validación de intentos y bloqueo a UsuarioService.
     *
     * @param correo Correo ingresado (identificador de usuario).
     * @param clave Clave ingresada.
     * @param session Manejador de la sesión del usuario.
     * @param model Modelo para inyectar mensajes en la vista Thymeleaf.
     * @return Redirección a /inicio si es exitoso o vuelve a login con el error.
     */
    @PostMapping("/login")
    public String procesarLogin(@RequestParam("correoPersonal") String correo,
                                @RequestParam("clave") String clave,
                                HttpSession session,
                                Model model) {
        try {
            // Llama a la capa de servicio para ejecutar la lógica de negocio y seguridad
            Usuario usuarioAutenticado = usuarioService.autenticar(correo, clave);

            // Guarda el objeto usuario en la sesión HTTP para mantener el estado
            session.setAttribute("usuarioLogueado", usuarioAutenticado);

            return "redirect:/inicio";

        } catch (Exception e) {
            // Si la autenticación falla (no existe, clave errónea o cuenta bloqueada)
            model.addAttribute("error", e.getMessage());
            model.addAttribute("correoIngresado", correo);
            return "login";
        }
    }

    /**
     * ========================================================================
     * VISTA DE REGISTRO
     * ========================================================================
     * Mapea la petición GET /registro. Prepara un objeto Usuario vacío en el
     * modelo para enlazarlo con el formulario (th:object).
     */
    @GetMapping("/registro")
    public String mostrarRegistro(Model model, HttpSession session) {
        if (session.getAttribute("usuarioLogueado") != null) {
            return "redirect:/inicio";
        }

        if (!model.containsAttribute("usuario")) {
            model.addAttribute("usuario", new Usuario());
        }
        return "registro";
    }

    /**
     * ========================================================================
     * PROCESAMIENTO DEL REGISTRO
     * ========================================================================
     * Mapea la petición POST /registro. Valida las anotaciones del Bean (@Valid)
     * y la unicidad de datos en UsuarioService.
     *
     * @param usuario Objeto mapeado desde el formulario.
     * @param result Captura errores de validación (@NotBlank, @Email, etc.).
     * @param redirectAttributes Permite pasar mensajes flash entre redirecciones.
     * @return Redirige al login con mensaje de éxito o recarga registro con errores.
     */
    @PostMapping("/registro")
    public String procesarRegistro(@Valid @ModelAttribute("usuario") Usuario usuario,
                                   BindingResult result,
                                   Model model,
                                   RedirectAttributes redirectAttributes) {

        // Validar errores sintácticos de entrada (campos vacíos, formato email, etc.)
        if (result.hasErrors()) {
            return "registro";
        }

        try {
            // Ejecutar la persistencia y reglas de unicidad en la capa de servicio
            usuarioService.registrarUsuario(usuario);

            // Mensaje de éxito para mostrar en la pantalla de login
            redirectAttributes.addFlashAttribute("exito", "¡Registro completado con éxito! Ya puedes iniciar sesión con tu correo.");
            return "redirect:/login";

        } catch (Exception e) {
            // Error de negocio (ej. documento o correo duplicado)
            model.addAttribute("error", e.getMessage());
            return "registro";
        }
    }

    /**
     * ========================================================================
     * CIERRE DE SESIÓN (LOGOUT)
     * ========================================================================
     * Invalida la sesión actual del usuario y lo redirige al formulario de login.
     */
    @GetMapping("/logout")
    public String cerrarSesion(HttpSession session, RedirectAttributes redirectAttributes) {
        session.invalidate();
        redirectAttributes.addFlashAttribute("info", "Has cerrado sesión correctamente.");
        return "redirect:/login";
    }
}