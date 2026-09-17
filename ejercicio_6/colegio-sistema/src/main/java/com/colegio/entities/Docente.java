package com.colegio.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * ============================================================================
 * ENTIDAD JPA AUDITADA: DOCENTE (USUARIO DEL SISTEMA)
 * ============================================================================
 * Capa: Modelo / Entidades JPA (ORM).
 *
 * Anotaciones:
 * - @Entity: Mapea la clase a una tabla relacional en MySQL.
 * - @Table(name = "docentes"): Define el nombre de la tabla en MySQL.
 * - @Audited: Hibernate Envers genera automáticamente la tabla "docentes_aud"
 *   para auditar cada INSERT, UPDATE o DELETE con su número de revisión.
 */
@Entity
@Table(name = "docentes")
@Audited
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"clave", "materias"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Docente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "apellido", nullable = false, length = 100)
    private String apellido;

    @Column(name = "sexo", nullable = false, length = 20)
    private String sexo; // Masculino, Femenino, Otro

    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    // El correo personal es el username de acceso al sistema escolar
    @Column(name = "correo_personal", nullable = false, unique = true, length = 150)
    private String correoPersonal;

    @Column(name = "clave", nullable = false, length = 255)
    private String clave;

    @Column(name = "rol", nullable = false, length = 50)
    @Builder.Default
    private String rol = "ROLE_DOCENTE";

    @Column(name = "activo", nullable = false)
    @Builder.Default
    private boolean activo = true;

    // Relación One-To-Many: Un docente puede dictar varias materias
    @OneToMany(mappedBy = "docente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Materia> materias = new ArrayList<>();
}