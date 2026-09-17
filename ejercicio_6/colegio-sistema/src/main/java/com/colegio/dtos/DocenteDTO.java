package com.colegio.dtos;

import lombok.*;

import java.time.LocalDate;

/**
 * ============================================================================
 * DTO DE SALIDA: DOCENTE (INFORMACIÓN PÚBLICA)
 * ============================================================================
 * Modela los datos de un docente para visualización en pantalla o selección.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocenteDTO {

    private Long id;
    private String nombre;
    private String apellido;
    private String nombreCompleto;
    private String sexo;
    private LocalDate fechaNacimiento;
    private String correoPersonal;
    private String rol;
}