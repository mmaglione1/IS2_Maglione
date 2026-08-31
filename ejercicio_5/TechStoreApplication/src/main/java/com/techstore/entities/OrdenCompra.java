package com.techstore.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * ============================================================================
 * ENTIDAD JPA: ORDEN DE COMPRA
 * ============================================================================
 * Cabecera de la orden emitida a un proveedor mayorista para la reposición de stock.
 */
@Entity
@Table(name = "ordenes_compra")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "detalles")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class OrdenCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "numero_orden", nullable = false, unique = true, length = 30)
    private String numeroOrden; // Ej: "OC-2026-0001"

    @Column(name = "fecha_emision", nullable = false)
    private LocalDateTime fechaEmision;

    @Column(name = "total", nullable = false)
    private Double total;

    @Column(name = "estado", nullable = false, length = 30)
    private String estado; // "REGISTRADA", "RECIBIDA", "CANCELADA"

    // Relación Many-To-One: La orden de compra pertenece a un proveedor específico
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proveedor_id", nullable = false)
    private Proveedor proveedor;

    // Composición One-To-Many: Si se persiste o elimina la orden, se gestionan sus detalles en cascada
    @OneToMany(mappedBy = "ordenCompra", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<DetalleOrden> detalles = new ArrayList<>();

    // Helper method para mantener la coherencia bidireccional
    public void agregarDetalle(DetalleOrden detalle) {
        this.detalles.add(detalle);
        detalle.setOrdenCompra(this);
    }
}