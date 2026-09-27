package com.colegio.dtos;

import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * ============================================================================
 * DTO DE ENTRADA: REGISTRO DE DOCENTE (USUARIO)
 * ============================================================================
 * Capa: Transferencia de Datos (DTO) - Entrada / Request.
 *
 * Modela el contrato del formulario de registro de profesores en Thymeleaf.
 * Valida los datos requeridos por la consigna antes de llegar al Service.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistroDocenteDTO {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(min = 2, max = 100, message = "El apellido debe tener entre 2 y 100 caracteres")
    private String apellido;

    @NotBlank(message = "Debe seleccionar el sexo")
    private String sexo; // "Masculino", "Femenino", "Otro"

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Past(message = "La fecha de nacimiento debe corresponder al pasado")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaNacimiento;

    // El correo personal funciona como identificador / username de acceso
    @NotBlank(message = "El correo personal es obligatorio")
    @Email(message = "Debe ingresar un formato de correo electrónico válido")
    @Size(max = 150, message = "El correo no puede exceder los 150 caracteres")
    private String correoPersonal;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, max = 50, message = "La contraseña debe tener al menos 6 caracteres")
    private String clave;

    @NotBlank(message = "Debe confirmar la contraseña")
    private String confirmarClave;
}