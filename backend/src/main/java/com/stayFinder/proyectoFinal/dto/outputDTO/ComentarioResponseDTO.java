package com.stayFinder.proyectoFinal.dto.outputDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Respuesta de comentario")
public class ComentarioResponseDTO {
    private Long id;
    private String texto;
    private String usuarioNombre;
    private String fecha;
}