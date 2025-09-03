package com.stayFinder.proyectoFinal.controller;

import com.stayFinder.proyectoFinal.dto.ControllerGeneralResponseDTO;
import com.stayFinder.proyectoFinal.dto.CreateReservaDTO;
import com.stayFinder.proyectoFinal.dto.ObtenerReservaDTO;
import com.stayFinder.proyectoFinal.entity.Reserva;
import com.stayFinder.proyectoFinal.security.UserDetailsImpl;

import com.stayFinder.proyectoFinal.services.Interfaces.ReservaServiceInterface;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reservas")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ReservaController {

    private ReservaServiceInterface reservaService;

    @GetMapping
    public ResponseEntity<List<ObtenerReservaDTO>> getAll(@AuthenticationPrincipal UserDetailsImpl user) throws Exception {
        List<ObtenerReservaDTO> reservas = reservaService.obtenerReservasUsuario(user.getId());
        return ResponseEntity.ok(reservas);
        
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reserva> getById(@PathVariable Long id) {
        Optional<Reserva> reserva = reservaService.findById(id);
        return reserva.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ControllerGeneralResponseDTO> create(@Valid @RequestBody CreateReservaDTO reserva, @AuthenticationPrincipal UserDetailsImpl user ) throws Exception {
       reservaService.createReserva(reserva, user.getId());
       return ResponseEntity.ok().body(new ControllerGeneralResponseDTO(true, "Reserva creada exitosamente"));

    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody Reserva reserva) {
        return reservaService.findById(id)
                .map(r -> {
                    reserva.setId(id);
                    return ResponseEntity.ok(reservaService.save(reserva));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (reservaService.findById(id).isPresent()) {
            reservaService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
