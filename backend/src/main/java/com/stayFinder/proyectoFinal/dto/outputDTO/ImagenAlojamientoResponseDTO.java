package com.stayFinder.proyectoFinal.dto.outputDTO;

import lombok.Data;

@Data
public class ImagenAlojamientoResponseDTO {
    private Long id;
    private String url;
    private Long alojamientoId;
}