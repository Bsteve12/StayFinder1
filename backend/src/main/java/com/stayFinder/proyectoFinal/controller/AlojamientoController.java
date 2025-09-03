package com.stayFinder.proyectoFinal.controller;

import com.stayFinder.proyectoFinal.dto.AlojamientoRequestDTO;
import com.stayFinder.proyectoFinal.dto.AlojamientoResponseDTO;
import com.stayFinder.proyectoFinal.services.Interfaces.AlojamientoServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/alojamientos")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AlojamientoController {

    private final AlojamientoServiceInterface alojamientoService;

    @PostMapping
    public ResponseEntity<AlojamientoResponseDTO> crear(@RequestBody AlojamientoRequestDTO req,
                                                        @RequestParam Long ownerId) {
        return ResponseEntity.ok(alojamientoService.crear(req, ownerId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlojamientoResponseDTO> editar(@PathVariable Long id,
                                                         @RequestBody AlojamientoRequestDTO req,
                                                         @RequestParam Long ownerId) {
        return ResponseEntity.ok(alojamientoService.editar(id, req, ownerId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id,
                                         @RequestParam Long ownerId) {
        alojamientoService.eliminar(id, ownerId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlojamientoResponseDTO> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(alojamientoService.obtenerPorId(id));
    }
}
