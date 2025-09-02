package com.stayFinder.proyectoFinal.controller;

import com.stayFinder.proyectoFinal.entity.Alojamiento;
import com.stayFinder.proyectoFinal.services.AlojamientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/alojamientos")
@CrossOrigin(origins = "http://localhost:4200")
public class AlojamientoController {

    @Autowired
    private AlojamientoService alojamientoService;

    @GetMapping
    public List<Alojamiento> getAll() {
        return alojamientoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Alojamiento> getById(@PathVariable Long id) {
        Optional<Alojamiento> alojamiento = alojamientoService.findById(id);
        return alojamiento.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Alojamiento create(@RequestBody Alojamiento alojamiento) {
        return alojamientoService.save(alojamiento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Alojamiento> update(@PathVariable Long id, @RequestBody Alojamiento alojamiento) {
        return alojamientoService.findById(id)
                .map(a -> {
                    alojamiento.setId(id);
                    return ResponseEntity.ok(alojamientoService.save(alojamiento));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (alojamientoService.findById(id).isPresent()) {
            alojamientoService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
