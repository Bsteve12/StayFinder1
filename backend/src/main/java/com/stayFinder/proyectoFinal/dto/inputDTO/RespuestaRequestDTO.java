package com.stayFinder.proyectoFinal.dto.inputDTO;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record RespuestaRequestDTO(
        Long comentarioId,
        String mensajeRespuesta
) {}