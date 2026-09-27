package com.colegio.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * ============================================================================
 * DTO DE ENTRADA: CAMBIO DE CONTRASEÑA
 * ============================================================================
 * Permite al docente autenticado modificar su clave en el sistema.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CambioPasswordDTO {

    @NotBlank(message = "Debe ingresar su contraseña actual")
    private String claveActual;

    @NotBlank(message = "Debe ingresar la nueva contraseña")
    @Size(min = 6, max = 50, message = "La nueva contraseña debe tener al menos 6 caracteres")
    private String claveNueva;

    @NotBlank(message = "Debe confirmar la nueva contraseña")
    private String confirmarClaveNueva;
}