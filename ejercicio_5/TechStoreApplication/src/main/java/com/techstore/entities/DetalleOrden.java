package com.techstore.entities;

import jakarta.persistence.*;
import lombok.*;

/**
 * ============================================================================
 * ENTIDAD JPA: DETALLE DE ORDEN DE COMPRA
 * ============================================================================
 * Representa cada renglón o ítem dentro de una orden de compra mayorista.
 */
@Entity
@Table(name = "detalles_orden")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DetalleOrden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    // Relación Many-To-One: Detalle perteneciente a una orden de compra
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orden_compra_id", nullable = false)
    private OrdenCompra ordenCompra;

    // Relación Many-To-One: Producto tecnológico adquirido
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad; // Cantidad a sumar al stock del producto

    @Column(name = "precio_unitario_compra", nullable = false)
    private Double precioUnitarioCompra;

    @Column(name = "subtotal", nullable = false)
    private Double subtotal;
}