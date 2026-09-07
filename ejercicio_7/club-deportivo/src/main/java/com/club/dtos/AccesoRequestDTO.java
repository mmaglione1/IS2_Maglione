package com.club.dtos;

import com.club.entities.TipoAcceso;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccesoRequestDTO {
    @NotBlank(message = "El DNI es obligatorio")
    private String dni;
    @NotNull(message = "Debe indicar el tipo de movimiento")
    private TipoAcceso tipoAcceso;
    private MultipartFile capturaRostro;
}