package com.stayFinder.proyectoFinal.dto.outputDTO;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Respuesta con la información de una reserva")
public record ObtenerReservaDTO(
        @Schema(description = "ID de la reserva", example = "50") Long id,
        @Schema(description = "Fecha de la reserva", example = "2025-10-15") String fecha,
        @Schema(description = "Precio total de la reserva", example = "450000.0") Double precioTotal,
        @Schema(description = "Nombre del alojamiento reservado", example = "Apartamento en el Centro") String nombreAlojamiento,
        @Schema(description = "Nombre del usuario que hizo la reserva", example = "Ana Torres") String nombreUsuario,
        @Schema(description = "Teléfono de contacto del usuario", example = "+57 3109876543") String telefonoUsuario
) { }
