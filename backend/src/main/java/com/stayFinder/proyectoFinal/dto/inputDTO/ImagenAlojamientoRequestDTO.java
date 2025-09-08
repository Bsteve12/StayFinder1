package com.stayFinder.proyectoFinal.dto.inputDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Datos de entrada para subir una imagen de alojamiento")
public class ImagenAlojamientoRequestDTO {
    @Schema(description = "ID del alojamiento al que pertenece la imagen", example = "5")
    private Long alojamientoId;
    @Schema(description = "URL de la imagen", example = "http://example.com/imagen1.jpg")
    private String url;
}