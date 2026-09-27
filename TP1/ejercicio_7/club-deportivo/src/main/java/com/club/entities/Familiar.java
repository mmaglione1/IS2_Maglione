package com.club.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;

/**
 * Entidad JPA: Miembro del grupo familiar de un socio titular.
 */
@Entity
@Table(name = "familiares")
@Audited
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Familiar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String dni;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 100)
    private String apellido;

    @Column(length = 50)
    private String parentesco; // Ej: Conyuge, Hijo/a, Padre

    @Lob
    @Column(name = "foto_rostro", columnDefinition = "LONGBLOB")
    private byte[] fotoRostro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "socio_id", nullable = false)
    private Socio socioTitular;
}