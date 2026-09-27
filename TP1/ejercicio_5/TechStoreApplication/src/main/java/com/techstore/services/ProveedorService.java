package com.techstore.services;

import com.techstore.dtos.ProveedorDTO;
import com.techstore.entities.Proveedor;
import com.techstore.repositories.ProveedorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ============================================================================
 * CAPA DE SERVICIO: PROVEEDORES MAYORISTAS
 * ============================================================================
 */
@Service
@RequiredArgsConstructor
public class ProveedorService {

    private final ProveedorRepository proveedorRepository;

    /**
     * Retorna la lista de todos los proveedores registrados en formato DTO.
     */
    @Transactional(readOnly = true)
    public List<ProveedorDTO> listarTodos() {
        return proveedorRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    /**
     * Mapper manual: Convierte Entidad JPA a ProveedorDTO.
     */
    public ProveedorDTO convertirADTO(Proveedor proveedor) {
        return ProveedorDTO.builder()
                .id(proveedor.getId())
                .cuit(proveedor.getCuit())
                .razonSocial(proveedor.getRazonSocial())
                .telefono(proveedor.getTelefono())
                .email(proveedor.getEmail())
                .direccion(proveedor.getDireccion())
                .build();
    }
}