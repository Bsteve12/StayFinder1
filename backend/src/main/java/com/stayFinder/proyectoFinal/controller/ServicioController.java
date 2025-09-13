package com.stayFinder.proyectoFinal.controller;

import com.stayFinder.proyectoFinal.dto.inputDTO.ServicioInputDTO;
import com.stayFinder.proyectoFinal.dto.outputDTO.ServicioOutputDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servicios")
public class ServicioController {

    @GetMapping
    @Operation(summary = "Listar todos los servicios", description = "Obtiene una lista de servicios disponibles")
    @ApiResponse(responseCode = "200", description = "Listado de servicios")
    public List<ServicioOutputDTO> listarServicios() {
        return List.of(
                new ServicioOutputDTO(1L, "WiFi", "Internet de alta velocidad"),
                new ServicioOutputDTO(2L, "Piscina", "Piscina privada para los huéspedes")
        );
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo servicio")
    @ApiResponse(responseCode = "201", description = "Servicio creado")
    public ServicioOutputDTO crearServicio(@RequestBody ServicioInputDTO dto) {
        return new ServicioOutputDTO(1L, dto.getNombre(), dto.getDescripcion());
    }
}
