package com.stayFinder.proyectoFinal.dto.inputDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Datos de entrada para crear un token de restablecimiento de contraseña")
public class PasswordResetTokenRequestDTO {

    @NotNull
    @Schema(description = "ID del usuario asociado", example = "15")
    private Long usuarioId;
}
