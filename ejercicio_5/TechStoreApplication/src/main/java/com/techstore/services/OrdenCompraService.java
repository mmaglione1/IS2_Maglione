package com.techstore.services;

import com.techstore.dtos.DetalleOrdenDTO;
import com.techstore.dtos.OrdenCompraDTO;
import com.techstore.entities.DetalleOrden;
import com.techstore.entities.OrdenCompra;
import com.techstore.entities.Producto;
import com.techstore.entities.Proveedor;
import com.techstore.repositories.OrdenCompraRepository;
import com.techstore.repositories.ProductoRepository;
import com.techstore.repositories.ProveedorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * ============================================================================
 * CAPA DE SERVICIO: GESTIÓN DE ÓRDENES DE COMPRA Y ACTUALIZACIÓN DE STOCK
 * ============================================================================
 * Implementa la regla central de negocio: Al emitir una orden de compra a un
 * proveedor mayorista, se valida el detalle y se incrementa el inventario de
 * los productos en MySQL.
 */
@Service
@RequiredArgsConstructor
public class OrdenCompraService {

    private final OrdenCompraRepository ordenCompraRepository;
    private final ProveedorRepository proveedorRepository;
    private final ProductoRepository productoRepository;

    /**
     * Procesa y registra una orden de compra completa.
     * @Transactional asegura que si ocurre cualquier error en un ítem, se revierte
     * tanto el guardado de la orden como la actualización de stock en MySQL.
     */
    @Transactional
    public OrdenCompraDTO registrarOrdenCompra(OrdenCompraDTO ordenDTO) {
        // 1. Validar existencia del Proveedor mayorista
        Proveedor proveedor = proveedorRepository.findById(ordenDTO.getProveedorId())
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado con ID: " + ordenDTO.getProveedorId()));

        if (ordenDTO.getDetalles() == null || ordenDTO.getDetalles().isEmpty()) {
            throw new RuntimeException("La orden debe contener al menos un detalle de producto.");
        }

        // 2. Construir la cabecera de la Orden de Compra
        String numeroComprobante = "OC-" + System.currentTimeMillis() % 1000000;
        OrdenCompra orden = OrdenCompra.builder()
                .numeroOrden(numeroComprobante)
                .fechaEmision(LocalDateTime.now())
                .proveedor(proveedor)
                .estado("REGISTRADA")
                .total(0.0)
                .build();

        double totalAcumulado = 0.0;

        // 3. Procesar cada renglón de detalle y actualizar el stock
        for (DetalleOrdenDTO detalleDTO : ordenDTO.getDetalles()) {
            Producto producto = productoRepository.findById(detalleDTO.getProductoId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + detalleDTO.getProductoId()));

            // Cálculo financiero del renglón
            double subtotal = detalleDTO.getCantidad() * detalleDTO.getPrecioUnitarioCompra();
            totalAcumulado += subtotal;

            DetalleOrden detalle = DetalleOrden.builder()
                    .producto(producto)
                    .cantidad(detalleDTO.getCantidad())
                    .precioUnitarioCompra(detalleDTO.getPrecioUnitarioCompra())
                    .subtotal(subtotal)
                    .build();

            // Vinculación bidireccional
            orden.agregarDetalle(detalle);

            // ACTUALIZACIÓN DE STOCK: Se suma la cantidad comprada al inventario
            int nuevoStock = producto.getStock() + detalleDTO.getCantidad();
            producto.setStock(nuevoStock);
            productoRepository.save(producto); // Persiste el nuevo stock en MySQL
        }

        orden.setTotal(totalAcumulado);

        // 4. Persistir la orden y sus detalles en cascada
        OrdenCompra ordenGuardada = ordenCompraRepository.save(orden);

        return convertirADTO(ordenGuardada);
    }

    /**
     * Retorna todas las órdenes emitidas ordenadas por fecha reciente.
     */
    @Transactional(readOnly = true)
    public List<OrdenCompraDTO> listarTodas() {
        return ordenCompraRepository.findAllByOrderByFechaEmisionDesc().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    /**
     * Mapper: Convierte Entidad OrdenCompra y sus detalles a OrdenCompraDTO.
     */
    private OrdenCompraDTO convertirADTO(OrdenCompra orden) {
        List<DetalleOrdenDTO> detallesDTO = orden.getDetalles().stream()
                .map(d -> DetalleOrdenDTO.builder()
                        .id(d.getId())
                        .productoId(d.getProducto().getId())
                        .productoNombre(d.getProducto().getNombre())
                        .productoSku(d.getProducto().getCodigoSku())
                        .cantidad(d.getCantidad())
                        .precioUnitarioCompra(d.getPrecioUnitarioCompra())
                        .subtotal(d.getSubtotal())
                        .build())
                .collect(Collectors.toList());

        return OrdenCompraDTO.builder()
                .id(orden.getId())
                .numeroOrden(orden.getNumeroOrden())
                .fechaEmision(orden.getFechaEmision())
                .proveedorId(orden.getProveedor().getId())
                .proveedorRazonSocial(orden.getProveedor().getRazonSocial())
                .total(orden.getTotal())
                .estado(orden.getEstado())
                .detalles(detallesDTO)
                .build();
    }
}