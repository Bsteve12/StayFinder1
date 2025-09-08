package com.stayFinder.proyectoFinal.dto.inputDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Datos de entrada para marcar un favorito")
public class FavoriteRequestDTO {
    @Schema(description = "ID del usuario", example = "2")
    private Long usuarioId;
    @Schema(description = "ID del alojamiento", example = "5")
    private Long alojamientoId;
}
