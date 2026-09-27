package com.techstore.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * ============================================================================
 * DATA TRANSFER OBJECT (DTO) - AUTENTICACIÓN
 * ============================================================================
 * Transporta las credenciales enviadas desde el formulario de login (Thymeleaf)
 * hacia la capa de Servicio para la verificación de acceso.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginDTO {

    @NotBlank(message = "El nombre de usuario o correo es obligatorio")
    private String username;

    @NotBlank(message = "La contraseña es obligatoria")
    private String clave;
}
