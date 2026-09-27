package com.techstore.services;

import com.techstore.dtos.LoginDTO;
import com.techstore.dtos.RegistroUsuarioDTO;
import com.techstore.entities.Usuario;
import com.techstore.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ============================================================================
 * CAPA DE SERVICIO: SEGURIDAD Y USUARIOS
 * ============================================================================
 * @Service: Registra la clase en el contenedor de Spring como componente de negocio.
 * Centraliza la lógica de autenticación y registro de personal de la empresa.
 */
@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    /**
     * Valida las credenciales ingresadas en el formulario de login.
     *
     * @param loginDTO DTO con username y clave en texto plano.
     * @return Entidad Usuario autenticada para almacenar en la sesión HTTP.
     * @throws RuntimeException si el usuario no existe, está inactivo o la clave no coincide.
     */
    @Transactional(readOnly = true)
    public Usuario autenticar(LoginDTO loginDTO) {
        Usuario usuario = usuarioRepository.findByUsername(loginDTO.getUsername())
                .orElseThrow(() -> new RuntimeException("Credenciales inválidas: el usuario no existe."));

        if (!usuario.isActivo()) {
            throw new RuntimeException("La cuenta de usuario se encuentra inhabilitada.");
        }

        // Validación de coincidencia de contraseña
        if (!usuario.getClave().equals(loginDTO.getClave())) {
            throw new RuntimeException("Credenciales inválidas: contraseña incorrecta.");
        }

        return usuario;
    }

    /**
     * Registra un nuevo operador o administrador en el sistema.
     *
     * @param registroDTO DTO con la información de alta validada sintácticamente.
     * @return Entidad Usuario persistida.
     * @throws RuntimeException si el nombre de usuario ya se encuentra registrado.
     */
    @Transactional
    public Usuario registrarUsuario(RegistroUsuarioDTO registroDTO) {
        if (usuarioRepository.existsByUsername(registroDTO.getUsername())) {
            throw new RuntimeException("El nombre de usuario '" + registroDTO.getUsername() + "' ya está en uso.");
        }

        Usuario nuevoUsuario = Usuario.builder()
                .nombreCompleto(registroDTO.getNombreCompleto())
                .username(registroDTO.getUsername())
                .clave(registroDTO.getClave())
                .rol(registroDTO.getRol() != null ? registroDTO.getRol() : "COMPRADOR")
                .activo(true)
                .build();

        return usuarioRepository.save(nuevoUsuario);
    }
}
