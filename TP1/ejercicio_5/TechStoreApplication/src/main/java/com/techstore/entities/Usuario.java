package com.techstore.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * ============================================================================
 * CAPA DE MODELO / ENTIDAD JPA (ORM)
 * ============================================================================
 * Mapea la tabla "usuarios" en MySQL. Administra las credenciales de acceso
 * y los estados de seguridad del personal del sistema.
 */
@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "clave") // Evita imprimir la clave en logs por seguridad
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "nombre_completo", nullable = false, length = 100)
    private String nombreCompleto;

    // Identificador único utilizado para el login
    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username;

    @Column(name = "clave", nullable = false, length = 255)
    private String clave;

    @Column(name = "rol", nullable = false, length = 30)
    private String rol; // Ej: "ADMIN", "COMPRADOR"

    @Builder.Default
    @Column(name = "activo", nullable = false)
    private boolean activo = true;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @PrePersist
    public void prePersist() {
        if (this.fechaCreacion == null) {
            this.fechaCreacion = LocalDateTime.now();
        }
    }
}