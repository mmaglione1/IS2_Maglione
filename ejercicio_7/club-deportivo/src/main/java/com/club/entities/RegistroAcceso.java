package com.club.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;
import java.time.LocalDateTime;

/**
 * Entidad JPA: Registro de entradas y salidas biométricas.
 */
@Entity
@Table(name = "registros_acceso")
@Audited
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistroAcceso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String dniPersona;

    @Column(nullable = false, length = 150)
    private String nombreCompleto;

    @Column(nullable = false)
    private LocalDateTime fechaHora;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15)
    private TipoAcceso tipoAcceso;

    @Lob
    @Column(name = "captura_rostro", columnDefinition = "LONGBLOB")
    private byte[] capturaRostro;
}