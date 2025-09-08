package com.stayFinder.proyectoFinal.dto.outputDTO;


import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor

public class RespuestaSolicitudDTO {
    private Long solicitudId;
    private Long adminId;
    private boolean aprobada;
    private String comentario;

    // Getters y Setters
}