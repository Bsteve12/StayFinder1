package com.stayFinder.proyectoFinal.controller;

import com.stayFinder.proyectoFinal.dto.inputDTO.PagoInputDTO;
import com.stayFinder.proyectoFinal.dto.outputDTO.PagoOutputDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/pagos")
public class PagoController {

    @GetMapping
    @Operation(summary = "Listar pagos realizados")
    @ApiResponse(responseCode = "200", description = "Listado de pagos")
    public List<PagoOutputDTO> listarPagos() {
        return List.of(
                new PagoOutputDTO(1L, 1L, "Tarjeta", 150.0, "PAGADO", LocalDateTime.now()),
                new PagoOutputDTO(2L, 2L, "PayPal", 200.0, "PENDIENTE", LocalDateTime.now())
        );
    }

    @PostMapping
    @Operation(summary = "Registrar un nuevo pago")
    @ApiResponse(responseCode = "201", description = "Pago registrado")
    public PagoOutputDTO crearPago(@RequestBody PagoInputDTO dto) {
        return new PagoOutputDTO(
                99L, dto.getReservaId(), dto.getMetodoPago(), dto.getMonto(), "PAGADO", LocalDateTime.now()
        );
    }
}
