package com.colegio.repositories;

import com.colegio.entities.Nota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * ============================================================================
 * CAPA DE PERSISTENCIA / REPOSITORIO DE NOTAS Y EVALUACIONES
 * ============================================================================
 */
@Repository
public interface NotaRepository extends JpaRepository<Nota, Long> {

    /**
     * Recupera el historial completo de notas de un alumno (Boletín escolar).
     * Consulta generada:
     * SELECT * FROM notas WHERE alumno_id = ? ORDER BY fecha_evaluacion DESC
     *
     * @param alumnoId Clave foránea del estudiante.
     * @return Lista de notas del alumno.
     */
    List<Nota> findByAlumnoIdOrderByFechaEvaluacionDesc(Long alumnoId);

    /**
     * Recupera todas las calificaciones emitidas en una materia específica.
     *
     * @param materiaId Clave foránea de la materia.
     * @return Lista de notas asentadas para la asignatura.
     */
    List<Nota> findByMateriaId(Long materiaId);

    /**
     * Busca si ya existe una nota asentada para un alumno, materia y período determinado.
     * Evita duplicar calificaciones en el mismo período evaluativo.
     */
    Optional<Nota> findByAlumnoIdAndMateriaIdAndPeriodo(Long alumnoId, Long materiaId, String periodo);
}