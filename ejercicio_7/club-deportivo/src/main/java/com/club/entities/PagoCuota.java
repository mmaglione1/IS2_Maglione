package com.club.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entidad JPA: Registro de pago de cuota para el grupo familiar.
 */
@Entity
@Table(name = "pagos_cuota")
@Audited
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagoCuota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate periodoMesAno; // Primer día del mes que abona

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal monto;

    @Column(nullable = false)
    private LocalDateTime fechaPago;

    @Enumerated(EnumType.STRING)
    @Column(name = "medio_pago", nullable = false, length = 30)
    private MedioPago medioPago;

    @Column(length = 100)
    private String comprobanteReferencia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "socio_id", nullable = false)
    private Socio socioTitular;
}