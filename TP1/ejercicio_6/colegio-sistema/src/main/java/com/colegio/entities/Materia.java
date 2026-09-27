package com.colegio.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;

import java.util.ArrayList;
import java.util.List;

/**
 * ============================================================================
 * ENTIDAD JPA AUDITADA: MATERIA / ASIGNATURA
 * ============================================================================
 * Modela las materias escolares y su vinculación con el docente a cargo.
 */
@Entity
@Table(name = "materias")
@Audited
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"docente", "notas"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Materia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "nombre", nullable = false, unique = true, length = 100)
    private String nombre;

    // Relación Many-To-One: La materia es dictada por un Docente
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "docente_id", nullable = false)
    private Docente docente;

    @OneToMany(mappedBy = "materia", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Nota> notas = new ArrayList<>();
}