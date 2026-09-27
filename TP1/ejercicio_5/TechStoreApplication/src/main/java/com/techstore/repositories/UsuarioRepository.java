package com.techstore.repositories;

import com.techstore.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * ============================================================================
 * CAPA DE PERSISTENCIA / REPOSITORIO DE USUARIOS
 * ============================================================================
 * Gestiona las operaciones de persistencia para la entidad Usuario en MySQL.
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Busca un usuario por su nombre de usuario (utilizado para el login).
     * Consulta generada: SELECT * FROM usuarios WHERE username = ?
     */
    Optional<Usuario> findByUsername(String username);

    /**
     * Verifica si ya existe un usuario con el identificador ingresado.
     * Consulta generada: SELECT COUNT(*) > 0 FROM usuarios WHERE username = ?
     */
    boolean existsByUsername(String username);
}
