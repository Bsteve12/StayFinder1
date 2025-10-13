package com.stayFinder.proyectoFinal.dto.outputDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IngresosPorAlojamientoResponseDTO {
    private String nombreAlojamiento;
    private Double ingresosTotales;
}
