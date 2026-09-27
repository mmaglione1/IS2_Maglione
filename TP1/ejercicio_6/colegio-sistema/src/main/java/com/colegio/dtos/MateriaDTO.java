package com.colegio.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * ============================================================================
 * DTO: MATERIA / ASIGNATURA
 * ============================================================================
 * Modela la materia curricular y su vínculo con el docente titular.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MateriaDTO {

    private Long id;

    @NotBlank(message = "El nombre de la materia es obligatorio")
    private String nombre;

    @NotNull(message = "Debe seleccionar un docente a cargo")
    private Long docenteId;

    private String docenteNombreCompleto;
}