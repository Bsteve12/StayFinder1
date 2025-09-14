package com.stayFinder.proyectoFinal.dto.inputDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Datos de entrada para crear un comentario en un alojamiento")
public class ComentarioRequestDTO {
    @Schema(description = "Texto del comentario", example = "Excelente alojamiento con muy buena atención")
    private String texto;

    @Schema(description = "ID del usuario que comenta", example = "2")
    private Long usuarioId;

    @Schema(description = "ID del alojamiento sobre el cual se comenta", example = "5")
    private Long alojamientoId;
}
