package com.club.dtos;

import com.club.entities.MedioPago;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagoCuotaRequestDTO {
    @NotNull(message = "El socio titular es obligatorio")
    private Long socioId;
    @NotNull(message = "El monto no puede ser nulo")
    @DecimalMin(value = "0.01", message = "El monto debe ser superior a 0")
    private BigDecimal monto;
    @NotNull(message = "Seleccione un medio de pago")
    private MedioPago medioPago;
    private String comprobanteReferencia;
    @NotNull(message = "Indique el mes abonado")
    private Integer mes;
    @NotNull(message = "Indique el año abonado")
    private Integer anio;
}