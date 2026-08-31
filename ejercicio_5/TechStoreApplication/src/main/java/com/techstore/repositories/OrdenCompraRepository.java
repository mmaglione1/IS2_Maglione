package com.techstore.repositories;

import com.techstore.entities.OrdenCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * ============================================================================
 * CAPA DE PERSISTENCIA / REPOSITORIO DE ÓRDENES DE COMPRA
 * ============================================================================
 */
@Repository
public interface OrdenCompraRepository extends JpaRepository<OrdenCompra, Long> {

    /**
     * Busca una orden por su número de comprobante único.
     */
    Optional<OrdenCompra> findByNumeroOrden(String numeroOrden);

    /**
     * Obtiene el listado completo de órdenes de compra ordenadas por fecha más reciente.
     * Consulta generada: SELECT * FROM ordenes_compra ORDER BY fecha_emision DESC
     */
    List<OrdenCompra> findAllByOrderByFechaEmisionDesc();
}