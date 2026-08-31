package com.sistema.usuarios.repositories;

import com.sistema.usuarios.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * ============================================================================
 * CAPA DE ACCESO A DATOS / PERSISTENCIA (Repository Layer)
 * ============================================================================
 * Al extender de JpaRepository<Usuario, Long>, Spring Data JPA genera en tiempo
 * de ejecución la implementación de todas las operaciones CRUD estándar:
 * - save(usuario): Insertar o actualizar.
 * - findById(id): Buscar por clave primaria.
 * - findAll(): Listar todos los registros.
 * - deleteById(id): Eliminar por clave primaria.
 *
 * @Repository: Anotación estereotipo de Spring que marca esta interfaz como un
 * componente de persistencia y habilita la traducción automática de excepciones
 * nativas de MySQL a excepciones genéricas de Spring (DataAccessException).
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Busca un usuario a partir de su correo personal.
     * En la consigna, el correo personal se utiliza como el nombre de usuario para el login.
     *
     * Spring Data JPA analiza el nombre del método ("findBy" + "CorreoPersonal") y
     * genera automáticamente la consulta SQL:
     * SELECT * FROM usuarios WHERE correo_personal = ?
     *
     * @param correoPersonal Correo ingresado en el login o registro.
     * @return Optional con el usuario si existe, o vacío si no está registrado.
     */
    Optional<Usuario> findByCorreoPersonal(String correoPersonal);

    /**
     * Verifica si ya existe un usuario con el documento indicado.
     * Consulta generada: SELECT COUNT(*) > 0 FROM usuarios WHERE documento = ?
     *
     * @param documento Documento a validar.
     * @return true si ya existe, false en caso contrario.
     */
    boolean existsByDocumento(String documento);

    /**
     * Verifica si ya existe un usuario con el correo personal indicado.
     * Consulta generada: SELECT COUNT(*) > 0 FROM usuarios WHERE correo_personal = ?
     *
     * @param correoPersonal Correo a validar.
     * @return true si ya existe, false en caso contrario.
     */
    boolean existsByCorreoPersonal(String correoPersonal);
}