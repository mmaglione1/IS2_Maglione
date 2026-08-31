package com.techstore.repositories;

import com.techstore.entities.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * ============================================================================
 * CAPA DE PERSISTENCIA / REPOSITORIO DE PROVEEDORES MAYORISTAS
 * ============================================================================
 */
@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {

    /**
     * Busca un proveedor por su número de CUIT.
     */
    Optional<Proveedor> findByCuit(String cuit);

    /**
     * Verifica si existe un proveedor registrado con el CUIT indicado.
     */
    boolean existsByCuit(String cuit);
}