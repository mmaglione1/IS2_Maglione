package com.club.dtos;

import com.club.entities.TipoAcceso;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccesoResponseDTO {
    private Long id;
    private String dni;
    private String nombreCompleto;
    private LocalDateTime fechaHora;
    private TipoAcceso tipoAcceso;
}