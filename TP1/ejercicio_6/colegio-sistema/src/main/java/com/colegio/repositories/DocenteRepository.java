package com.colegio.repositories;

import com.colegio.entities.Docente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * ============================================================================
 * CAPA DE PERSISTENCIA / REPOSITORIO DE DOCENTES (USUARIOS)
 * ============================================================================
 * Maneja las operaciones de acceso a datos para la entidad auditada Docente.
 * Provee la consulta principal de autenticación mediante el correo personal.
 */
@Repository
public interface DocenteRepository extends JpaRepository<Docente, Long> {

    /**
     * Busca un docente por su correo personal (utilizado como username en el login).
     * Consulta generada por Spring Data JPA:
     * SELECT * FROM docentes WHERE correo_personal = ?
     *
     * @param correoPersonal Correo único del docente.
     * @return Optional con la entidad Docente encontrada.
     */
    Optional<Docente> findByCorreoPersonal(String correoPersonal);

    /**
     * Valida si un correo ya se encuentra registrado para evitar duplicados.
     * Consulta generada:
     * SELECT COUNT(*) > 0 FROM docentes WHERE correo_personal = ?
     *
     * @param correoPersonal Correo a verificar.
     * @return true si ya existe, false en caso contrario.
     */
    boolean existsByCorreoPersonal(String correoPersonal);
}