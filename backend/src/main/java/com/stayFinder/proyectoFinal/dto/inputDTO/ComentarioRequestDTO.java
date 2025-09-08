// ComentarioRequestDTO.java
package com.stayFinder.proyectoFinal.dto.inputDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Datos de entrada para crear un comentario")
public class ComentarioRequestDTO {
    @Schema(description = "Texto del comentario", example = "Excelente alojamiento")
    private String texto;
    @Schema(description = "ID del usuario que comenta", example = "2")
    private Long usuarioId;
    @Schema(description = "ID del alojamiento/publicación", example = "5")
    private Long alojamientoId;
}

