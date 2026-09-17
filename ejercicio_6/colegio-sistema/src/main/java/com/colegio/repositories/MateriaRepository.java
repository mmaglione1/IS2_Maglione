package com.colegio.repositories;

import com.colegio.entities.Materia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * ============================================================================
 * CAPA DE PERSISTENCIA / REPOSITORIO DE MATERIAS
 * ============================================================================
 */
@Repository
public interface MateriaRepository extends JpaRepository<Materia, Long> {

    /**
     * Busca una materia por su nombre exacto.
     */
    Optional<Materia> findByNombre(String nombre);

    /**
     * Valida si ya existe una asignatura con ese nombre.
     */
    boolean existsByNombre(String nombre);

    /**
     * Obtiene las materias a cargo de un docente determinado.
     * Consulta generada:
     * SELECT * FROM materias WHERE docente_id = ?
     *
     * @param docenteId Identificador del docente titular.
     * @return Lista de materias dictadas por el docente.
     */
    List<Materia> findByDocenteId(Long docenteId);
}