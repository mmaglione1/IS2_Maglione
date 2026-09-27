package com.techstore.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * ============================================================================
 * DATA TRANSFER OBJECT (DTO) - RENGLÓN / DETALLE DE ORDEN DE COMPRA
 * ============================================================================
 * Representa cada ítem cargado dentro del formulario de la orden de compra.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetalleOrdenDTO {

    private Long id;

    @NotNull(message = "Debe seleccionar un producto válido")
    private Long productoId;

    // Campos informativos para renderizar en la vista Thymeleaf
    private String productoNombre;
    private String productoSku;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser al menos 1 unidad")
    private Integer cantidad;

    @NotNull(message = "El precio de compra unitario es obligatorio")
    @Min(value = 0, message = "El precio de compra no puede ser negativo")
    private Double precioUnitarioCompra;

    private Double subtotal;
}
