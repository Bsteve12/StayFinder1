package com.stayFinder.proyectoFinal.dto.outputDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO genérico para devolver resultados de reportes")
public class ReporteDTO {
    @Schema(description = "Nombre del reporte generado", example = "Reservas por Anfitrión")
    private String nombreReporte;

    @Schema(description = "Descripción del reporte", example = "Lista de reservas agrupadas por anfitrión en el último mes")
    private String descripcion;

    @Schema(description = "Datos del reporte, puede ser una lista o un mapa", example = "[{\"anfitrion\":\"Juan\",\"reservas\":5}]")
    private Object datos; // Puede ser una lista, un mapa, etc.
}
