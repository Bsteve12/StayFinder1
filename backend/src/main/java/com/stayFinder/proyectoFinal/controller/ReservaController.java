package com.stayFinder.proyectoFinal.controller;

import com.stayFinder.proyectoFinal.entity.Reserva;
import com.stayFinder.proyectoFinal.services.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reserva")
@CrossOrigin(origins = "http://localhost:4200")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    // GET localhost:8080/api/reserva
    @GetMapping
    public List<Reserva> getAllReservas() {
        return reservaService.findAll();
    }

    // GET localhost:8080/api/reserva/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Reserva> getReservaById(@PathVariable Long id) {
        Optional<Reserva> reserva = reservaService.findById(id);
        return reserva.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST localhost:8080/api/reserva
    @PostMapping
    public ResponseEntity<Reserva> createReserva(@RequestBody Reserva reserva) {
        try {
            Reserva nuevaReserva = reservaService.save(reserva);
            return ResponseEntity.ok(nuevaReserva);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // PUT localhost:8080/api/reserva/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Reserva> updateReserva(@PathVariable Long id, @RequestBody Reserva reserva) {
        Optional<Reserva> existente = reservaService.findById(id);
        if (existente.isPresent()) {
            reserva.setId(id);
            try {
                Reserva actualizada = reservaService.save(reserva);
                return ResponseEntity.ok(actualizada); // 🔥 corregido
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE localhost:8080/api/reserva/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReserva(@PathVariable Long id) {
        Optional<Reserva> existente = reservaService.findById(id);
        if (existente.isPresent()) {
            reservaService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
