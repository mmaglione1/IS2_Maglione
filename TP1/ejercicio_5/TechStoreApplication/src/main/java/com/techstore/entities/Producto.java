package com.techstore.entities;

import jakarta.persistence.*;
import lombok.*;

/**
 * ============================================================================
 * ENTIDAD JPA: PRODUCTO
 * ============================================================================
 * Representa los productos tecnológicos del catálogo y almacena el stock actual.
 */
@Entity
@Table(name = "productos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "codigo_sku", nullable = false, unique = true, length = 50)
    private String codigoSku; // Código único de producto (ej: "TEC-NOTE-001")

    @Column(name = "nombre", nullable = false, length = 150)
    private String nombre;

    @Column(name = "categoria", nullable = false, length = 50)
    private String categoria; // Ej: "Laptops", "Componentes", "Periféricos"

    @Column(name = "precio_venta", nullable = false)
    private Double precioVenta;

    // Stock actual en almacén (se incrementa automáticamente al recibir órdenes de compra)
    @Column(name = "stock", nullable = false)
    private Integer stock;

    @Builder.Default
    @Column(name = "activo", nullable = false)
    private boolean activo = true;
}