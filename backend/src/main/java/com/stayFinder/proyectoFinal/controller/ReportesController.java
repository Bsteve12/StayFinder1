package com.stayFinder.proyectoFinal.controller;

import com.stayFinder.proyectoFinal.dto.outputDTO.ReporteDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/reportes")
@Tag(name = "Reportes", description = "Consultas y reportes multitabla del sistema")
@CrossOrigin(origins = "http://localhost:4200")
public class ReportesController {

    @GetMapping("/reservas-por-usuario")
    @Operation(summary = "Reporte de reservas por usuario",
            description = "Obtiene todas las reservas agrupadas por cada usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reporte generado exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ReporteDTO> reservasPorUsuario() {
        ReporteDTO reporte = new ReporteDTO("Reservas por Usuario", "Muestra la cantidad de reservas por usuario", new ArrayList<>());
        return ResponseEntity.ok(reporte);
    }

    @GetMapping("/ingresos-por-alojamiento")
    @Operation(summary = "Reporte de ingresos por alojamiento",
            description = "Calcula los ingresos totales generados por cada alojamiento")
    public ResponseEntity<ReporteDTO> ingresosPorAlojamiento() {
        ReporteDTO reporte = new ReporteDTO("Ingresos por Alojamiento", "Total de ingresos generados por alojamiento", new ArrayList<>());
        return ResponseEntity.ok(reporte);
    }

    @GetMapping("/publicaciones-pendientes")
    @Operation(summary = "Reporte de publicaciones pendientes",
            description = "Lista todas las publicaciones que están en estado pendiente de aprobación")
    public ResponseEntity<ReporteDTO> publicacionesPendientes() {
        ReporteDTO reporte = new ReporteDTO("Publicaciones Pendientes", "Listado de publicaciones aún no aprobadas", new ArrayList<>());
        return ResponseEntity.ok(reporte);
    }

    @GetMapping("/usuarios-activos")
    @Operation(summary = "Reporte de usuarios activos",
            description = "Muestra los usuarios que han iniciado sesión o realizado acciones en el último mes")
    public ResponseEntity<ReporteDTO> usuariosActivos() {
        ReporteDTO reporte = new ReporteDTO("Usuarios Activos", "Usuarios activos en el último mes", new ArrayList<>());
        return ResponseEntity.ok(reporte);
    }

    @GetMapping("/favoritos-por-usuario")
    @Operation(summary = "Reporte de favoritos por usuario",
            description = "Muestra los alojamientos marcados como favoritos por cada usuario")
    public ResponseEntity<ReporteDTO> favoritosPorUsuario() {
        ReporteDTO reporte = new ReporteDTO("Favoritos por Usuario", "Alojamientos favoritos agrupados por usuario", new ArrayList<>());
        return ResponseEntity.ok(reporte);
    }
}
