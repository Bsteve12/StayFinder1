package com.stayFinder.proyectoFinal.dto.outputDTO;

import java.time.LocalDateTime;

public record ComentarioResponseDTO(
        String nombreUsuario,
        String mensaje,
        Integer calificacion,
        String respuestaAnfitrion,
        String nombreAnfitrion,
        LocalDateTime fechaCreacion
) {}
