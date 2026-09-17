package com.colegio.repositories;

import com.colegio.entities.Aula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * ============================================================================
 * CAPA DE PERSISTENCIA / REPOSITORIO DE AULAS Y GRADOS
 * ============================================================================
 */
@Repository
public interface AulaRepository extends JpaRepository<Aula, Long> {

    /**
     * Busca un aula por el nombre del grado y su número físico.
     *
     * @param nombreGrado Nombre del curso (ej. "1er Grado").
     * @param numeroAula Número o identificador (ej. "Aula 101").
     * @return Optional con el Aula localizada.
     */
    Optional<Aula> findByNombreGradoAndNumeroAula(String nombreGrado, String numeroAula);

    /**
     * Verifica la existencia de una sección de grado y aula.
     */
    boolean existsByNombreGradoAndNumeroAula(String nombreGrado, String numeroAula);
}