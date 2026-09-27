package com.techstore.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * ============================================================================
 * DATA TRANSFER OBJECT (DTO) - REGISTRO DE USUARIO
 * ============================================================================
 * Captura y valida los datos de alta de usuarios antes de transformarlos
 * en una entidad de dominio en la capa de Servicio.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistroUsuarioDTO {

    @NotBlank(message = "El nombre completo es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    private String nombreCompleto;

    @NotBlank(message = "El nombre de usuario es obligatorio")
    @Size(min = 4, max = 50, message = "El usuario debe tener al menos 4 caracteres")
    private String username;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 4, message = "La contraseña debe tener al menos 4 caracteres")
    private String clave;

    @NotBlank(message = "El rol es obligatorio")
    private String rol; // Ej: "ADMIN", "COMPRADOR"
}
