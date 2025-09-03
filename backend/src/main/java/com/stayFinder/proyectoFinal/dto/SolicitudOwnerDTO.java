package com.stayFinder.proyectoFinal.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder

public class SolicitudOwnerDTO {
    private Long usuarioId;
    private String comentario;

    // Getters y Setters
}
