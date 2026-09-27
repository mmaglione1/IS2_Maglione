package com.club.dtos;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SocioResponseDTO {
    private Long id;
    private String dni;
    private String nombreCompleto;
    private String email;
    private boolean tieneFoto;
    private List<String> familiaresNombres;
}