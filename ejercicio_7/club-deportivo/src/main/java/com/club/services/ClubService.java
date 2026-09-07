package com.club.services;

import com.club.dtos.*;
import java.util.List;

public interface ClubService {
    SocioResponseDTO registrarSocio(SocioRequestDTO dto);
    void agregarFamiliar(FamiliarDTO dto);
    List<SocioResponseDTO> listarSocios();
    AccesoResponseDTO registrarAcceso(AccesoRequestDTO dto);
    List<AccesoResponseDTO> ultimosAccesos();
    PagoCuotaResponseDTO registrarPago(PagoCuotaRequestDTO dto);
    List<PagoCuotaResponseDTO> listarPagos();
    byte[] obtenerFotoSocio(Long id);
    byte[] obtenerFotoAcceso(Long id);
}