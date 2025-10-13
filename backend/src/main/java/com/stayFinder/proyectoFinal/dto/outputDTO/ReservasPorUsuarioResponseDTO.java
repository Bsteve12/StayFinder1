package com.stayFinder.proyectoFinal.dto.outputDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Muestra el nombre del usuario y la cantidad de reservas que tiene  ")
public class ReservasPorUsuarioResponseDTO {
    private String nombreUsuario;
    private Long cantidadReservas;
}
