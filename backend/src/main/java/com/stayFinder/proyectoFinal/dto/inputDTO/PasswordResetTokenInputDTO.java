package com.stayFinder.proyectoFinal.dto.inputDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Datos para generar un token de recuperación de contraseña")
public class PasswordResetTokenInputDTO {
    @NotNull
    @Schema(description = "ID del usuario que solicita la recuperación", example = "4")
    private Long usuarioId;

    @NotBlank
    @Schema(description = "Token único de recuperación", example = "abc123xyz789")
    private String token;
}
