package com.club.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FamiliarDTO {
    private Long id;
    @NotNull(message = "El socio titular es requerido")
    private Long socioTitularId;
    @NotBlank(message = "El DNI es obligatorio")
    private String dni;
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;
    @NotBlank(message = "El parentesco es obligatorio")
    private String parentesco;
    private MultipartFile foto;
}