package com.colegio.dtos;

import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * ============================================================================
 * DTO: CALIFICACIÓN / NOTA ACADÉMICA
 * ============================================================================
 * Transporta la información de carga de notas y su exposición en boletines.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotaDTO {

    private Long id;

    @NotNull(message = "La calificación es obligatoria")
    @DecimalMin(value = "1.0", message = "La calificación mínima es 1.0")
    @DecimalMax(value = "10.0", message = "La calificación máxima es 10.0")
    private Double calificacion;

    @NotBlank(message = "El período de evaluación es obligatorio")
    private String periodo; // Ej: "1° Trimestre", "Examen Parcial", "Final"

    @NotNull(message = "La fecha de evaluación es obligatoria")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaEvaluacion;

    @NotNull(message = "Debe seleccionar un alumno")
    private Long alumnoId;
    private String alumnoNombreCompleto;
    private String alumnoDocumento;

    @NotNull(message = "Debe seleccionar una materia")
    private Long materiaId;
    private String materiaNombre;

    private String gradoAulaInfo;
}