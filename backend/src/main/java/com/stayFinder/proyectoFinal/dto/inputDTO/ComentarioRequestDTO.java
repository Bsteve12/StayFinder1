package com.stayFinder.proyectoFinal.dto.inputDTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record ComentarioRequestDTO(
        Long reservaId,
        Integer calificacion,
        String mensaje
) {}