package com.sistema.usuarios.services;

import com.sistema.usuarios.entities.Usuario;
import com.sistema.usuarios.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * ============================================================================
 * CAPA DE SERVICIO / LÓGICA DE NEGOCIO (Service Layer)
 * ============================================================================
 * @Service: Marca la clase como componente de servicio en el contenedor IoC de Spring.
 * Esta capa es el "cerebro" de la aplicación: procesa los datos, aplica las reglas
 * de validación y coordina las operaciones con la capa de persistencia (UsuarioRepository).
 */
@Service
public class UsuarioService {

    // Inyección de dependencias de la capa de persistencia
    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * ========================================================================
     * REGLA DE NEGOCIO: REGISTRO DE USUARIOS
     * ========================================================================
     * Valida la unicidad del documento y del correo personal antes de guardar.
     * @Transactional: Garantiza que la operación sea atómica; si ocurre un error,
     * se realiza un rollback automático en la base de datos MySQL.
     *
     * @param usuario Instancia con los datos personales a registrar.
     * @return El usuario persistido en MySQL.
     * @throws Exception Si el correo o el documento ya se encuentran registrados.
     */
    @Transactional
    public Usuario registrarUsuario(Usuario usuario) throws Exception {
        // Validación de correo personal único
        if (usuarioRepository.existsByCorreoPersonal(usuario.getCorreoPersonal().trim())) {
            throw new Exception("El correo personal '" + usuario.getCorreoPersonal() + "' ya está registrado en el sistema.");
        }

        // Validación de documento único
        if (usuarioRepository.existsByDocumento(usuario.getDocumento().trim())) {
            throw new Exception("El documento '" + usuario.getDocumento() + "' ya se encuentra registrado.");
        }

        // Inicialización de estados de negocio
        usuario.setCorreoPersonal(usuario.getCorreoPersonal().trim().toLowerCase());
        usuario.setIntentosFallidos(0);
        usuario.setBloqueado(false);

        return usuarioRepository.save(usuario);
    }

    /**
     * ========================================================================
     * REGLA DE NEGOCIO: AUTENTICACIÓN, CONTEO DE INTENTOS Y BLOQUEO
     * ========================================================================
     * Lógica solicitada por la consigna:
     * 1. Si el usuario no existe: se le solicita registrarse.
     * 2. Si el usuario está registrado y bloqueado: se impide el acceso.
     * 3. Si la clave es incorrecta: se incrementa el contador de intentos fallidos.
     *    Al 3er fallo consecutivo, la cuenta se bloquea inmediatamente.
     * 4. Si la clave es correcta: se resetean los intentos fallidos a 0.
     *
     * @param correoPersonal Identificador de usuario ingresado en el login.
     * @param clave Contraseña ingresada.
     * @return Usuario autenticado con éxito.
     * @throws Exception Describiendo el motivo del rechazo (no registrado, bloqueado o clave incorrecta).
     */
    @Transactional
    public Usuario autenticar(String correoPersonal, String clave) throws Exception {
        String correoLimpio = (correoPersonal != null) ? correoPersonal.trim().toLowerCase() : "";

        // 1. Verificar si el usuario está registrado en el sistema
        Optional<Usuario> optionalUsuario = usuarioRepository.findByCorreoPersonal(correoLimpio);

        if (optionalUsuario.isEmpty()) {
            throw new Exception("El correo no se encuentra registrado en el sistema. Por favor, regístrese.");
        }

        Usuario usuario = optionalUsuario.get();

        // 2. Verificar si la cuenta ya se encuentra bloqueada
        if (usuario.isBloqueado()) {
            throw new Exception("Su cuenta ha sido BLOQUEADA por superar los 3 intentos fallidos permitidos. Contacte al administrador.");
        }

        // 3. Validar la clave ingresada
        if (!usuario.getClave().equals(clave)) {
            // Incrementar contador de intentos fallidos
            int intentos = usuario.getIntentosFallidos() + 1;
            usuario.setIntentosFallidos(intentos);

            // Si llega a 3 intentos fallidos, se bloquea la cuenta
            if (intentos >= 3) {
                usuario.setBloqueado(true);
                usuarioRepository.save(usuario);
                throw new Exception("Ha ingresado una clave errónea 3 veces consecutivas. Su cuenta ha sido BLOQUEADA.");
            } else {
                usuarioRepository.save(usuario);
                int restantes = 3 - intentos;
                throw new Exception("Clave incorrecta. Le quedan " + restantes + " intento(s) antes del bloqueo de su cuenta.");
            }
        }

        // 4. Autenticación exitosa: se resetea el contador de intentos fallidos a 0
        if (usuario.getIntentosFallidos() > 0) {
            usuario.setIntentosFallidos(0);
            usuarioRepository.save(usuario);
        }

        return usuario;
    }

    /**
     * Busca un usuario por su ID para operaciones de consulta.
     */
    @Transactional(readOnly = true)
    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }
}