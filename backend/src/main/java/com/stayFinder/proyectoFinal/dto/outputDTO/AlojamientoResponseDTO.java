package com.stayFinder.proyectoFinal.dto.outputDTO;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Respuesta con los datos de un alojamiento")
public record AlojamientoResponseDTO(
        @Schema(description = "ID del alojamiento", example = "10") Long id,
        @Schema(description = "Nombre del alojamiento", example = "Casa en el Lago") String nombre,
        @Schema(description = "Dirección del alojamiento", example = "Calle 123 #45-67, Armenia") String direccion,
        @Schema(description = "Precio por noche", example = "150000.0") Double precio,
        @Schema(description = "Descripción detallada del alojamiento", example = "Hermosa casa con vista al lago y piscina privada") String descripcion,
        @Schema(description = "ID del propietario", example = "3") Long ownerId
) { }
