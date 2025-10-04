package com.stayFinder.proyectoFinal.controller;

import com.stayFinder.proyectoFinal.dto.outputDTO.ControllerGeneralResponseDTO;
import com.stayFinder.proyectoFinal.dto.inputDTO.ReservaRequestDTO;
import com.stayFinder.proyectoFinal.dto.outputDTO.ReservaResponseDTO;
import com.stayFinder.proyectoFinal.security.UserDetailsImpl;
import com.stayFinder.proyectoFinal.services.reservaService.interfaces.ReservaServiceInterface;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reservas")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "Reservas", description = "Operaciones de creación, consulta, edición y eliminación de reservas")
public class ReservaController {

    private final ReservaServiceInterface reservaService;

    @GetMapping
    @Operation(summary = "Listar reservas del usuario autenticado", description = "Devuelve las reservas asociadas al usuario logueado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de reservas"),
            @ApiResponse(responseCode = "401", description = "No autenticado")
    })
    public ResponseEntity<List<ReservaResponseDTO>> getAll(
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl user) throws Exception {
        List<ReservaResponseDTO> reservas = reservaService.obtenerReservasUsuario(user.getId());
        return ResponseEntity.ok(reservas);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener reserva por ID", description = "Devuelve los datos de una reserva por su ID.")
    public ResponseEntity<ReservaResponseDTO> getById(
            @Parameter(description = "ID de la reserva", required = true, example = "1")
            @PathVariable Long id) {
        Optional<ReservaResponseDTO> reserva = reservaService.findById(id);
        return reserva.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear reserva", description = "Crea una nueva reserva para el usuario autenticado.")
    public ResponseEntity<ReservaResponseDTO> create(
            @Valid @RequestBody ReservaRequestDTO reserva,
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl user) throws Exception {

        ReservaResponseDTO response = reservaService.createReserva(reserva, user.getId());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar reserva", description = "Actualiza una reserva (ej. cambios de fechas).")
    public ResponseEntity<Object> update(
            @Parameter(description = "ID de la reserva", required = true, example = "1")
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Objeto con los cambios de reserva")
            @RequestBody ReservaRequestDTO reserva) throws Exception {
        ReservaResponseDTO updated = reservaService.save(reserva);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar reserva", description = "Elimina una reserva por id.")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID de la reserva", required = true, example = "1")
            @PathVariable Long id,
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl user) throws Exception {
        reservaService.deleteById(id, user.getId());
        return ResponseEntity.noContent().build();
    }
}
