package com.stayFinder.proyectoFinal.dto.outputDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO genérico para devolver resultados de reportes.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReporteDTO {
    private String nombreReporte;
    private String descripcion;
    private Object datos; // Puede ser una lista, un mapa, etc.
}
