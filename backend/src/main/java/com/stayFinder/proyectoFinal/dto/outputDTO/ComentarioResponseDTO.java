package com.stayFinder.proyectoFinal.dto.outputDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Respuesta con la información de un comentario")
public class ComentarioResponseDTO {
    @Schema(description = "ID del comentario", example = "20")
    private Long id;

    @Schema(description = "Texto del comentario", example = "Muy cómodo y excelente atención")
    private String texto;

    @Schema(description = "Nombre del usuario que comentó", example = "Carlos Gómez")
    private String usuarioNombre;

    @Schema(description = "Fecha del comentario", example = "2025-09-10T15:30:00")
    private String fecha;
}
