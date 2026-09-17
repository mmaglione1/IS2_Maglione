package com.colegio.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;

import java.time.LocalDate;

/**
 * ============================================================================
 * ENTIDAD JPA AUDITADA: NOTA / CALIFICACIÓN
 * ============================================================================
 * Modela la calificación obtenida por un alumno en una materia específica.
 * Al estar anotada con @Audited, si un docente modifica o recalifica una nota,
 * Hibernate Envers guarda el valor anterior, el nuevo y la fecha de cambio.
 */
@Entity
@Table(name = "notas")
@Audited
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"alumno", "materia"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Nota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "calificacion", nullable = false)
    private Double calificacion; // Calificación numérica (ej. 8.5)

    @Column(name = "periodo", nullable = false, length = 50)
    private String periodo; // Ej: "1° Trimestre", "Examen Final"

    @Column(name = "fecha_evaluacion", nullable = false)
    private LocalDate fechaEvaluacion;

    // Relación Many-To-One: La nota pertenece a un Alumno específico
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alumno_id", nullable = false)
    private Alumno alumno;

    // Relación Many-To-One: La nota corresponde a una Materia específica
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "materia_id", nullable = false)
    private Materia materia;
}