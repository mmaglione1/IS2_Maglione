package com.techstore.repositories;

import com.techstore.entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * ============================================================================
 * CAPA DE PERSISTENCIA / REPOSITORIO DE PRODUCTOS
 * ============================================================================
 */
@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    /**
     * Busca un producto por su código SKU único.
     */
    Optional<Producto> findByCodigoSku(String codigoSku);

    /**
     * Verifica la existencia de un producto por SKU para evitar duplicados.
     */
    boolean existsByCodigoSku(String codigoSku);

    /**
     * Recupera todos los productos activos para mostrar en el catálogo y formularios.
     * Consulta generada: SELECT * FROM productos WHERE activo = true
     */
    List<Producto> findByActivoTrue();
}