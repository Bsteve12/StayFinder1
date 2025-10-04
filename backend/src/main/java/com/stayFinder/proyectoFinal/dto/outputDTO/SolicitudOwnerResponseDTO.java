package com.stayFinder.proyectoFinal.dto.outputDTO;

import com.stayFinder.proyectoFinal.entity.enums.EstadoSolicitud;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Respuesta con datos de la solicitud Owner")
public record SolicitudOwnerResponseDTO(
        Long id,
        String nombreUsuario,
        EstadoSolicitud estado,
        String comentario,
        String documentoRuta,
        LocalDateTime fechaSolicitud,
        LocalDateTime fechaRevision
) {}
