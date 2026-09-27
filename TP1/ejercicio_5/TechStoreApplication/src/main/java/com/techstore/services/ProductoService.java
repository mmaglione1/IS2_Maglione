package com.techstore.services;

import com.techstore.dtos.ProductoDTO;
import com.techstore.entities.Producto;
import com.techstore.repositories.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ============================================================================
 * CAPA DE SERVICIO: PRODUCTOS Y CATÁLOGO DE TECNOLOGÍA
 * ============================================================================
 */
@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;

    /**
     * Recupera todos los productos activos transformados a DTO.
     */
    @Transactional(readOnly = true)
    public List<ProductoDTO> listarProductosActivos() {
        return productoRepository.findByActivoTrue().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca un producto por ID y lo retorna en formato DTO.
     */
    @Transactional(readOnly = true)
    public ProductoDTO buscarPorId(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));
        return convertirADTO(producto);
    }

    /**
     * Registra un nuevo producto tecnológico en la base de datos.
     */
    @Transactional
    public ProductoDTO crearProducto(ProductoDTO dto) {
        if (productoRepository.existsByCodigoSku(dto.getCodigoSku())) {
            throw new RuntimeException("Ya existe un producto con el código SKU: " + dto.getCodigoSku());
        }

        Producto producto = Producto.builder()
                .codigoSku(dto.getCodigoSku())
                .nombre(dto.getNombre())
                .categoria(dto.getCategoria())
                .precioVenta(dto.getPrecioVenta())
                .stock(dto.getStock())
                .activo(true)
                .build();

        Producto guardado = productoRepository.save(producto);
        return convertirADTO(guardado);
    }

    /**
     * Mapper manual: Convierte Entidad JPA a ProductoDTO.
     */
    public ProductoDTO convertirADTO(Producto producto) {
        return ProductoDTO.builder()
                .id(producto.getId())
                .codigoSku(producto.getCodigoSku())
                .nombre(producto.getNombre())
                .categoria(producto.getCategoria())
                .precioVenta(producto.getPrecioVenta())
                .stock(producto.getStock())
                .activo(producto.isActivo())
                .build();
    }
}
