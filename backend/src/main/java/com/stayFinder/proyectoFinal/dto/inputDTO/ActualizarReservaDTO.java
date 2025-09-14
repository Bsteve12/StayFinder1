package com.stayFinder.proyectoFinal.dto.inputDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Datos para actualizar una reserva existente")
public class ActualizarReservaDTO {
    @Schema(description = "Nuevo estado de la reserva (0 = Pendiente, 1 = Confirmada, 2 = Cancelada)", example = "1")
    private Integer estado;

    @Schema(description = "Nueva fecha de la reserva en formato YYYY-MM-DD", example = "2025-10-15")
    private String fecha;
}
