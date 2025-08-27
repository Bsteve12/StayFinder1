package com.stayFinder.proyectoFinal.controller;

import com.stayFinder.proyectoFinal.entity.Reserva;
import com.stayFinder.proyectoFinal.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "http://localhost:4200")

public class ReservaController {

    @Autowired
    private ProductoService productoService;

    @GetMapping // GET localhost:8080/api/reserva
    public List<Reserva> getAllProductos() {
        return reservaService.findAll();
    }

    @GetMapping("/{id}") // GET localhost:8080/api/reserva/{id}
    public ResponseEntity<Reserva> getReservaById(@PathVariable Long id) {
        Optional<Reserva> producto = reservaService.findById(id);
        return reserva.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Reserva> createProducto(@RequestBody Reserva reserva) {
        try {
            Reserva nuevaReserva = reservaService.save(reserva);
            return ResponseEntity.ok(nuevaReserva);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reserva> updateProducto(@PathVariable Long id, @RequestBody Reserva reserva) {
        Optional<Reserva> existente = reservaService.findById(id);
        if (existente.isPresent()) {
            reserva.setId(id);
            try {
                Reserva actualizada = reservaService.save(reserva);
                return ResponseEntity.ok(actualizado);
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

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