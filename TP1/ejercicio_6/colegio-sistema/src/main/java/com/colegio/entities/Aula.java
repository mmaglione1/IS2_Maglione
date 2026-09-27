package com.colegio.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;

import java.util.ArrayList;
import java.util.List;

/**
 * ============================================================================
 * ENTIDAD JPA AUDITADA: AULA Y GRADO ESCOLAR
 * ============================================================================
 * Modela el grado y el aula física del colegio donde cursan los alumnos.
 */
@Entity
@Table(name = "aulas")
@Audited
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "alumnos")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Aula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "nombre_grado", nullable = false, length = 50)
    private String nombreGrado; // Ej: "1er Año", "5to Grado"

    @Column(name = "numero_aula", nullable = false, length = 20)
    private String numeroAula; // Ej: "Aula 104", "Laboratorio A"

    @OneToMany(mappedBy = "aula", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Alumno> alumnos = new ArrayList<>();
}