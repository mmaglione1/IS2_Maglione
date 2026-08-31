package com.sistema.usuarios.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * ============================================================================
 * CAPA DE MODELO / ENTIDAD JPA (Object-Relational Mapping - ORM)
 * ============================================================================
 * Esta clase representa la tabla "usuarios" en la base de datos MySQL.
 * Hibernate se encarga de traducir cada instancia de esta clase en una fila
 * de la tabla correspondiente y cada atributo en una columna.
 */
@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "clave") // Excluye la contraseña del toString por seguridad
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Usuario {

    /**
     * ========================================================================
     * IDENTIFICADOR ÚNICO (Primary Key)
     * ========================================================================
     * @Id: Marca el campo como la clave primaria de la tabla.
     * @GeneratedValue(strategy = GenerationType.IDENTITY): Delega la generación
     * del ID a MySQL mediante la propiedad AUTO_INCREMENT.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    /**
     * ========================================================================
     * DATOS PERSONALES REQUERIDOS POR LA CONSIGNA
     * ========================================================================
     */

    // Nombre de la persona
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    // Apellido de la persona
    @NotBlank(message = "El apellido es obligatorio")
    @Size(min = 2, max = 50, message = "El apellido debe tener entre 2 y 50 caracteres")
    @Column(name = "apellido", nullable = false, length = 50)
    private String apellido;

    // Documento Nacional de Identidad (DNI/Cédula/Pasaporte)
    @NotBlank(message = "El documento es obligatorio")
    @Size(min = 6, max = 20, message = "El documento debe tener entre 6 y 20 caracteres")
    @Column(name = "documento", nullable = false, unique = true, length = 20)
    private String documento;

    // Fecha de Nacimiento del usuario
    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Past(message = "La fecha de nacimiento debe ser una fecha pasada")
    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    // Correo Personal: Funciona como el IDENTIFICADOR DE USUARIO (username) para el login
    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "Debe proporcionar un formato de correo electrónico válido")
    @Column(name = "correo_personal", nullable = false, unique = true, length = 100)
    private String correoPersonal;

    // Clave de acceso al sistema
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 4, message = "La contraseña debe tener al menos 4 caracteres")
    @Column(name = "clave", nullable = false, length = 255)
    private String clave;

    /**
     * ========================================================================
     * CAMPOS ADICIONALES DE REGISTRO
     * ========================================================================
     */

    // Teléfono de contacto de la persona
    @Column(name = "telefono", length = 20)
    private String telefono;

    // Dirección o domicilio de residencia
    @Column(name = "direccion", length = 150)
    private String direccion;

    /**
     * ========================================================================
     * CAMPOS DE CONTROL DE NEGOCIO Y SEGURIDAD
     * ========================================================================
     */

    // Contador de intentos fallidos de autenticación (Se bloquea al llegar a 3)
    @Builder.Default
    @Column(name = "intentos_fallidos", nullable = false)
    private int intentosFallidos = 0;

    // Estado del usuario en el sistema: true = bloqueado, false = activo/habilitado
    @Builder.Default
    @Column(name = "bloqueado", nullable = false)
    private boolean bloqueado = false;

    // Auditoría: Fecha y hora exacta de registro
    @Column(name = "fecha_registro", nullable = false, updatable = false)
    private LocalDateTime fechaRegistro;

    /**
     * ========================================================================
     * CALLBACK DE CICLO DE VIDA JPA
     * ========================================================================
     * @PrePersist: Se ejecuta automáticamente antes de insertar el registro en la base de datos.
     */
    @PrePersist
    public void prePersist() {
        if (this.fechaRegistro == null) {
            this.fechaRegistro = LocalDateTime.now();
        }
    }
}