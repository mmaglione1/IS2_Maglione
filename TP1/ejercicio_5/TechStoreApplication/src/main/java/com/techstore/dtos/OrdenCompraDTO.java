package com.techstore.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * ============================================================================
 * DATA TRANSFER OBJECT (DTO) - ORDEN DE COMPRA COMPLETA
 * ============================================================================
 * Agrupa la cabecera y el detalle de la compra para procesar la transacción
 * y la actualización del stock en la capa de Servicio.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrdenCompraDTO {

    private Long id;
    private String numeroOrden;
    private LocalDateTime fechaEmision;

    @NotNull(message = "Debe seleccionar un proveedor mayorista")
    private Long proveedorId;

    // Campo informativo para mostrar en la vista
    private String proveedorRazonSocial;

    private Double total;
    private String estado;

    // Lista de ítems de compra contenidos en la orden
    @Valid
    @NotEmpty(message = "La orden de compra debe incluir al menos un detalle de producto")
    @Builder.Default
    private List<DetalleOrdenDTO> detalles = new ArrayList<>();
}