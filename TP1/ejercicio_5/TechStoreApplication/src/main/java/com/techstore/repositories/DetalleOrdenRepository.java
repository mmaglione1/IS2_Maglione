package com.techstore.repositories;

import com.techstore.entities.DetalleOrden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ============================================================================
 * CAPA DE PERSISTENCIA / REPOSITORIO DE DETALLES DE COMPRA
 * ============================================================================
 */
@Repository
public interface DetalleOrdenRepository extends JpaRepository<DetalleOrden, Long> {

    /**
     * Recupera los ítems o renglones asociados a una orden de compra específica.
     */
    List<DetalleOrden> findByOrdenCompraId(Long ordenCompraId);
}
