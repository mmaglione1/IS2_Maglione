package com.colegio.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * ============================================================================
 * DTO: AULA Y GRADO ESCOLAR
 * ============================================================================
 * Transporta el grado y número de aula física para asignación de estudiantes.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AulaDTO {

    private Long id;

    @NotBlank(message = "El nombre del grado es obligatorio")
    private String nombreGrado;

    @NotBlank(message = "El número o nombre del aula es obligatorio")
    private String numeroAula;

    private String descripcionCompleta; // Ej: "1er Año - Aula 102"
    private int totalAlumnos;
}