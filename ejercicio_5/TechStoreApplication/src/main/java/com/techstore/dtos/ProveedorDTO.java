package com.techstore.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * ============================================================================
 * DATA TRANSFER OBJECT (DTO) - PROVEEDOR MAYORISTA
 * ============================================================================
 * Transporta los datos de la empresa proveedora de tecnología.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProveedorDTO {

    private Long id;

    @NotBlank(message = "El CUIT es obligatorio")
    private String cuit;

    @NotBlank(message = "La razón social es obligatoria")
    private String razonSocial;

    private String telefono;
    private String email;
    private String direccion;
}