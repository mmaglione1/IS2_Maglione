package com.club.controllers;

import com.club.config.SecurityConfig;
import com.club.dtos.PagoCuotaRequestDTO;
import com.club.dtos.PagoCuotaResponseDTO;
import com.club.dtos.SocioResponseDTO;
import com.club.entities.MedioPago;
import com.club.services.ClubService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Pruebas unitarias de la capa Web MVC para PagoController.
 * Valida autorizaciones de Spring Security, renderizado de vistas y procesamiento de DTOs.
 */
@WebMvcTest(PagoController.class)
@Import(SecurityConfig.class)
class PagoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClubService clubService;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("GET /pagos - Debe retornar la vista del historial para rol ADMIN")
    void testHistorialPagosAdmin() throws Exception {
        PagoCuotaResponseDTO pagoDTO = PagoCuotaResponseDTO.builder()
                .id(1L)
                .titularNombreCompleto("Lionel Messi")
                .titularDni("40123456")
                .periodo("9/2026")
                .monto(new BigDecimal("15000.00"))
                .fechaPago(LocalDateTime.now())
                .medioPago(MedioPago.MERCADO_PAGO)
                .build();

        when(clubService.listarPagos()).thenReturn(List.of(pagoDTO));

        mockMvc.perform(get("/pagos"))
                .andExpect(status().isOk())
                .andExpect(view().name("pagos/historial"))
                .andExpect(model().attributeExists("pagos"));
    }

    @Test
    @WithMockUser(username = "porteria", roles = {"USER"})
    @DisplayName("GET /pagos - Debe denegar acceso (403 Forbidden) para rol USER")
    void testHistorialPagosForbiddenParaOperador() throws Exception {
        mockMvc.perform(get("/pagos"))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("GET /pagos - Debe redirigir al login si no está autenticado")
    void testHistorialPagosSinAutenticacion() throws Exception {
        mockMvc.perform(get("/pagos"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("GET /pagos/nuevo - Debe cargar el formulario con socios y medios de pago")
    void testNuevoPagoForm() throws Exception {
        SocioResponseDTO socioDTO = SocioResponseDTO.builder()
                .id(1L)
                .dni("40123456")
                .nombreCompleto("Lionel Messi")
                .build();

        when(clubService.listarSocios()).thenReturn(List.of(socioDTO));

        mockMvc.perform(get("/pagos/nuevo"))
                .andExpect(status().isOk())
                .andExpect(view().name("pagos/form"))
                .andExpect(model().attributeExists("pagoDTO"))
                .andExpect(model().attributeExists("socios"))
                .andExpect(model().attributeExists("mediosPago"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("POST /pagos/guardar - Debe registrar pago válido y redirigir a /pagos")
    void testGuardarPagoExitoso() throws Exception {
        PagoCuotaResponseDTO responseDTO = PagoCuotaResponseDTO.builder()
                .id(10L)
                .titularNombreCompleto("Lionel Messi")
                .build();

        when(clubService.registrarPago(any(PagoCuotaRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/pagos/guardar")
                        .with(csrf())
                        .param("socioId", "1")
                        .param("monto", "15000.00")
                        .param("medioPago", "EFECTIVO")
                        .param("mes", "9")
                        .param("anio", "2026")
                        .param("comprobanteReferencia", "REC-001"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/pagos"))
                .andExpect(flash().attributeExists("exito"));

        verify(clubService).registrarPago(any(PagoCuotaRequestDTO.class));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("POST /pagos/guardar - Con errores de validación debe redirigir al formulario")
    void testGuardarPagoInvalido() throws Exception {
        mockMvc.perform(post("/pagos/guardar")
                        .with(csrf())
                        .param("socioId", "") // Campo obligatorio vacío
                        .param("monto", "-50.00") // Monto inválido
                        .param("medioPago", "TRANSFERENCIA")
                        .param("mes", "9")
                        .param("anio", "2026"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/pagos/nuevo"));
    }
}