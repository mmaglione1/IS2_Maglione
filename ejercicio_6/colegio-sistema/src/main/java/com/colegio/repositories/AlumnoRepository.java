package com.colegio.repositories;

import com.colegio.entities.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * ============================================================================
 * CAPA DE PERSISTENCIA / REPOSITORIO DE ALUMNOS
 * ============================================================================
 */
@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, Long> {

    /**
     * Busca un estudiante mediante su número de documento único.
     *
     * @param documento DNI / Identificación del alumno.
     * @return Optional con el Alumno correspondiente.
     */
    Optional<Alumno> findByDocumento(String documento);

    /**
     * Verifica si ya existe un alumno registrado con dicho documento.
     */
    boolean existsByDocumento(String documento);

    /**
     * Recupera todos los alumnos pertenecientes a un grado y aula específicos.
     * Consulta generada:
     * SELECT * FROM alumnos WHERE aula_id = ?
     *
     * @param aulaId Clave foránea del aula.
     * @return Lista de alumnos cursantes.
     */
    List<Alumno> findByAulaId(Long aulaId);
}