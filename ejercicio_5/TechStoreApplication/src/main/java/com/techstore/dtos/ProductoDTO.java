package com.techstore.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * ============================================================================
 * DATA TRANSFER OBJECT (DTO) - PRODUCTO TECNOLÓGICO
 * ============================================================================
 * Transporta los datos de los artículos comercializados entre el Service y la Vista.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoDTO {

    private Long id;

    @NotBlank(message = "El código SKU es obligatorio")
    private String codigoSku;

    @NotBlank(message = "El nombre del producto es obligatorio")
    private String nombre;

    @NotBlank(message = "La categoría es obligatoria")
    private String categoria;

    @NotNull(message = "El precio de venta es obligatorio")
    @Min(value = 0, message = "El precio debe ser mayor o igual a 0")
    private Double precioVenta;

    @NotNull(message = "El stock inicial es obligatorio")
    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stock;

    private boolean activo;
}