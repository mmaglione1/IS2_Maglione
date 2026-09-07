package com.club.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad JPA: Socio Principal del Club.
 * Anotada con @Audited para generar historial en la tabla socio_aud vía Hibernate Envers.
 */
@Entity
@Table(name = "socios")
@Audited
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Socio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String dni;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 100)
    private String apellido;

    @Column(length = 150)
    private String email;

    /**
     * Almacenamiento directo del rostro en formato binario MySQL LONGBLOB.
     */
    @Lob
    @Column(name = "foto_rostro", columnDefinition = "LONGBLOB")
    private byte[] fotoRostro;

    @OneToMany(mappedBy = "socioTitular", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Familiar> familiares = new ArrayList<>();

    @OneToMany(mappedBy = "socioTitular", cascade = CascadeType.ALL)
    @Builder.Default
    private List<PagoCuota> pagos = new ArrayList<>();
}