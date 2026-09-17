package com.colegio.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * ============================================================================
 * DTO: ALUMNO (ENTRADA Y SALIDA)
 * ============================================================================
 * Modela tanto la recepción del formulario de alta/edición de alumnos
 * como la renderización en las tablas de la vista escolar.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlumnoDTO {

    private Long id;

    @NotBlank(message = "El documento es obligatorio")
    @Size(min = 5, max = 20, message = "El documento debe tener entre 5 y 20 caracteres")
    private String documento;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(min = 2, max = 100, message = "El apellido debe tener entre 2 y 100 caracteres")
    private String apellido;

    private String nombreCompleto;

    // ID del aula seleccionada en el formulario
    @NotNull(message = "Debe asignar un grado y aula al alumno")
    private Long aulaId;

    // Campos aplanados para visualización en tablas HTML
    private String nombreGrado;
    private String numeroAula;
    private String ubicacionGradoAula; // Ej: "3er Grado - Aula 204"
}