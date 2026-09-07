package com.club.services.impl;

import com.club.dtos.*;
import com.club.entities.*;
import com.club.repositories.*;
import com.club.services.ClubService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Implementación de la Capa de Lógica de Negocio.
 * Orquesta transacciones, conversión hacia/desde DTOs y persistencia ORM.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ClubServiceImpl implements ClubService {

    private final SocioRepository socioRepository;
    private final FamiliarRepository familiarRepository;
    private final RegistroAccesoRepository accesoRepository;
    private final PagoCuotaRepository pagoRepository;

    @Override
    public SocioResponseDTO registrarSocio(SocioRequestDTO dto) {
        if (socioRepository.existsByDni(dto.getDni())) {
            throw new IllegalArgumentException("Ya existe un socio con DNI: " + dto.getDni());
        }

        byte[] bytesFoto = null;
        if (dto.getFoto() != null && !dto.getFoto().isEmpty()) {
            try {
                bytesFoto = dto.getFoto().getBytes();
            } catch (IOException e) {
                throw new RuntimeException("Error al procesar la imagen facial", e);
            }
        }

        Socio socio = Socio.builder()
                .dni(dto.getDni())
                .nombre(dto.getNombre())
                .apellido(dto.getApellido())
                .email(dto.getEmail())
                .fotoRostro(bytesFoto)
                .build();

        Socio guardado = socioRepository.save(socio);
        return mapearSocioResponse(guardado);
    }

    @Override
    public void agregarFamiliar(FamiliarDTO dto) {
        Socio titular = socioRepository.findById(dto.getSocioTitularId())
                .orElseThrow(() -> new IllegalArgumentException("Socio titular no encontrado"));

        if (familiarRepository.existsByDni(dto.getDni())) {
            throw new IllegalArgumentException("El familiar ya se encuentra registrado con DNI: " + dto.getDni());
        }

        byte[] bytesFoto = null;
        if (dto.getFoto() != null && !dto.getFoto().isEmpty()) {
            try {
                bytesFoto = dto.getFoto().getBytes();
            } catch (IOException e) {
                throw new RuntimeException("Error al procesar la imagen facial del familiar", e);
            }
        }

        Familiar familiar = Familiar.builder()
                .dni(dto.getDni())
                .nombre(dto.getNombre())
                .apellido(dto.getApellido())
                .parentesco(dto.getParentesco())
                .socioTitular(titular)
                .fotoRostro(bytesFoto)
                .build();

        familiarRepository.save(familiar);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SocioResponseDTO> listarSocios() {
        return socioRepository.findAll().stream()
                .map(this::mapearSocioResponse)
                .toList();
    }

    @Override
    public AccesoResponseDTO registrarAcceso(AccesoRequestDTO dto) {
        String nombreCompleto = "No identificado";
        byte[] capturaFoto = null;

        // Búsqueda cruzada de la persona por DNI: titular o familiar
        Optional<Socio> socioOpt = socioRepository.findByDni(dto.getDni());
        if (socioOpt.isPresent()) {
            Socio s = socioOpt.get();
            nombreCompleto = s.getNombre() + " " + s.getApellido() + " (Titular)";
            capturaFoto = s.getFotoRostro();
        } else {
            Optional<Familiar> famOpt = familiarRepository.findByDni(dto.getDni());
            if (famOpt.isPresent()) {
                Familiar f = famOpt.get();
                nombreCompleto = f.getNombre() + " " + f.getApellido() + " (Familiar)";
                capturaFoto = f.getFotoRostro();
            } else {
                throw new IllegalArgumentException("DNI no asociado a ningún socio ni familiar activo");
            }
        }

        // Si se envió captura instantánea en la solicitud, se prioriza
        if (dto.getCapturaRostro() != null && !dto.getCapturaRostro().isEmpty()) {
            try {
                capturaFoto = dto.getCapturaRostro().getBytes();
            } catch (IOException ignored) {}
        }

        RegistroAcceso acceso = RegistroAcceso.builder()
                .dniPersona(dto.getDni())
                .nombreCompleto(nombreCompleto)
                .fechaHora(LocalDateTime.now())
                .tipoAcceso(dto.getTipoAcceso())
                .capturaRostro(capturaFoto)
                .build();

        RegistroAcceso guardado = accesoRepository.save(acceso);

        return AccesoResponseDTO.builder()
                .id(guardado.getId())
                .dni(guardado.getDniPersona())
                .nombreCompleto(guardado.getNombreCompleto())
                .fechaHora(guardado.getFechaHora())
                .tipoAcceso(guardado.getTipoAcceso())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccesoResponseDTO> ultimosAccesos() {
        return accesoRepository.findTop30ByOrderByFechaHoraDesc().stream()
                .map(a -> AccesoResponseDTO.builder()
                        .id(a.getId())
                        .dni(a.getDniPersona())
                        .nombreCompleto(a.getNombreCompleto())
                        .fechaHora(a.getFechaHora())
                        .tipoAcceso(a.getTipoAcceso())
                        .build())
                .toList();
    }

    @Override
    public PagoCuotaResponseDTO registrarPago(PagoCuotaRequestDTO dto) {
        Socio titular = socioRepository.findById(dto.getSocioId())
                .orElseThrow(() -> new IllegalArgumentException("Socio titular no encontrado"));

        LocalDate periodo = LocalDate.of(dto.getAnio(), dto.getMes(), 1);

        PagoCuota pago = PagoCuota.builder()
                .socioTitular(titular)
                .monto(dto.getMonto())
                .periodoMesAno(periodo)
                .fechaPago(LocalDateTime.now())
                .medioPago(dto.getMedioPago())
                .comprobanteReferencia(dto.getComprobanteReferencia())
                .build();

        PagoCuota guardado = pagoRepository.save(pago);

        return PagoCuotaResponseDTO.builder()
                .id(guardado.getId())
                .titularNombreCompleto(titular.getNombre() + " " + titular.getApellido())
                .titularDni(titular.getDni())
                .periodo(dto.getMes() + "/" + dto.getAnio())
                .monto(guardado.getMonto())
                .fechaPago(guardado.getFechaPago())
                .medioPago(guardado.getMedioPago())
                .comprobanteReferencia(guardado.getComprobanteReferencia())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PagoCuotaResponseDTO> listarPagos() {
        return pagoRepository.findAllByOrderByFechaPagoDesc().stream()
                .map(p -> PagoCuotaResponseDTO.builder()
                        .id(p.getId())
                        .titularNombreCompleto(p.getSocioTitular().getNombre() + " " + p.getSocioTitular().getApellido())
                        .titularDni(p.getSocioTitular().getDni())
                        .periodo(p.getPeriodoMesAno().getMonthValue() + "/" + p.getPeriodoMesAno().getYear())
                        .monto(p.getMonto())
                        .fechaPago(p.getFechaPago())
                        .medioPago(p.getMedioPago())
                        .comprobanteReferencia(p.getComprobanteReferencia())
                        .build())
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public byte[] obtenerFotoSocio(Long id) {
        return socioRepository.findById(id).map(Socio::getFotoRostro).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public byte[] obtenerFotoAcceso(Long id) {
        return accesoRepository.findById(id).map(RegistroAcceso::getCapturaRostro).orElse(null);
    }

    private SocioResponseDTO mapearSocioResponse(Socio s) {
        return SocioResponseDTO.builder()
                .id(s.getId())
                .dni(s.getDni())
                .nombreCompleto(s.getNombre() + " " + s.getApellido())
                .email(s.getEmail())
                .tieneFoto(s.getFotoRostro() != null && s.getFotoRostro().length > 0)
                .familiaresNombres(s.getFamiliares().stream()
                        .map(f -> f.getNombre() + " (" + f.getParentesco() + ")")
                        .toList())
                .build();
    }
}