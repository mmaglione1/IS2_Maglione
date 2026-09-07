package com.club.dtos;

import com.club.entities.MedioPago;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagoCuotaResponseDTO {
    private Long id;
    private String titularNombreCompleto;
    private String titularDni;
    private String periodo;
    private BigDecimal monto;
    private LocalDateTime fechaPago;
    private MedioPago medioPago;
    private String comprobanteReferencia;
}