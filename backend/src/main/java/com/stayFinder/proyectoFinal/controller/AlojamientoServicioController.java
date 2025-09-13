package com.stayFinder.proyectoFinal.controller;

import com.stayFinder.proyectoFinal.dto.inputDTO.AlojamientoServicioInputDTO;
import com.stayFinder.proyectoFinal.dto.outputDTO.AlojamientoServicioOutputDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alojamiento-servicios")
public class AlojamientoServicioController {

    @GetMapping
    @Operation(summary = "Listar relaciones alojamiento-servicio", description = "Obtiene todos los servicios asociados a los alojamientos")
    @ApiResponse(responseCode = "200", description = "Listado de relaciones")
    public List<AlojamientoServicioOutputDTO> listarRelaciones() {
        return List.of(
                new AlojamientoServicioOutputDTO(1L, 1L, "WiFi"),
                new AlojamientoServicioOutputDTO(1L, 2L, "Piscina")
        );
    }

    @PostMapping
    @Operation(summary = "Asignar un servicio a un alojamiento")
    @ApiResponse(responseCode = "201", description = "Relación creada")
    public AlojamientoServicioOutputDTO crearRelacion(@RequestBody AlojamientoServicioInputDTO dto) {
        return new AlojamientoServicioOutputDTO(dto.getAlojamientoId(), dto.getServicioId(), "Ejemplo Servicio");
    }
}
