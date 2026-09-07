package com.club.services;

import com.club.dtos.AccesoRequestDTO;
import com.club.dtos.AccesoResponseDTO;
import com.club.dtos.PagoCuotaRequestDTO;
import com.club.dtos.PagoCuotaResponseDTO;
import com.club.entities.*;
import com.club.repositories.*;
import com.club.services.impl.ClubServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClubServiceTest {

    @Mock
    private SocioRepository socioRepository;
    @Mock
    private FamiliarRepository familiarRepository;
    @Mock
    private RegistroAccesoRepository accesoRepository;
    @Mock
    private PagoCuotaRepository pagoRepository;

    @InjectMocks
    private ClubServiceImpl clubService;

    private Socio socioMock;

    @BeforeEach
    void setUp() {
        socioMock = Socio.builder()
                .id(1L)
                .dni("40123456")
                .nombre("Lionel")
                .apellido("Messi")
                .email("lio@club.com")
                .fotoRostro(new byte[]{1, 2, 3})
                .build();
    }

    @Test
    @DisplayName("Debe registrar acceso exitosamente si el DNI pertenece a un socio titular")
    void testRegistrarAccesoExitoso() {
        when(socioRepository.findByDni("40123456")).thenReturn(Optional.of(socioMock));
        when(accesoRepository.save(any(RegistroAcceso.class))).thenAnswer(i -> {
            RegistroAcceso r = i.getArgument(0);
            r.setId(10L);
            return r;
        });

        AccesoRequestDTO dto = AccesoRequestDTO.builder()
                .dni("40123456")
                .tipoAcceso(TipoAcceso.ENTRADA)
                .build();

        AccesoResponseDTO response = clubService.registrarAcceso(dto);

        assertNotNull(response);
        assertEquals("40123456", response.getDni());
        assertEquals("Lionel Messi (Titular)", response.getNombreCompleto());
        verify(accesoRepository, times(1)).save(any(RegistroAcceso.class));
    }

    @Test
    @DisplayName("Debe lanzar excepción si el DNI no está registrado en el club")
    void testRegistrarAccesoDniInexistente() {
        when(socioRepository.findByDni("99999999")).thenReturn(Optional.empty());
        when(familiarRepository.findByDni("99999999")).thenReturn(Optional.empty());

        AccesoRequestDTO dto = AccesoRequestDTO.builder()
                .dni("99999999")
                .tipoAcceso(TipoAcceso.ENTRADA)
                .build();

        assertThrows(IllegalArgumentException.class, () -> clubService.registrarAcceso(dto));
        verify(accesoRepository, never()).save(any(RegistroAcceso.class));
    }

    @Test
    @DisplayName("Debe registrar el pago de cuota con diferentes medios de pago")
    void testRegistrarPagoCuota() {
        when(socioRepository.findById(1L)).thenReturn(Optional.of(socioMock));
        when(pagoRepository.save(any(PagoCuota.class))).thenAnswer(i -> {
            PagoCuota p = i.getArgument(0);
            p.setId(50L);
            return p;
        });

        PagoCuotaRequestDTO request = PagoCuotaRequestDTO.builder()
                .socioId(1L)
                .monto(new BigDecimal("15000.00"))
                .mes(4)
                .anio(2026)
                .medioPago(MedioPago.MERCADO_PAGO)
                .comprobanteReferencia("MP-123456")
                .build();

        PagoCuotaResponseDTO response = clubService.registrarPago(request);

        assertNotNull(response);
        assertEquals(MedioPago.MERCADO_PAGO, response.getMedioPago());
        assertEquals(new BigDecimal("15000.00"), response.getMonto());
        verify(pagoRepository, times(1)).save(any(PagoCuota.class));
    }
}