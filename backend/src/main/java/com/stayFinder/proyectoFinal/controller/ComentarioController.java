package com.stayFinder.proyectoFinal.controller;

import com.stayFinder.proyectoFinal.dto.inputDTO.ComentarioRequestDTO;
import com.stayFinder.proyectoFinal.dto.outputDTO.ComentarioResponseDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comentarios")
@Tag(name = "Comentarios", description = "Gestión de comentarios en alojamientos/publicaciones")
public class ComentarioController {

    @PostMapping
    @Operation(summary = "Crear comentario", description = "Crea un comentario en un alojamiento/publicación")
    public ResponseEntity<ComentarioResponseDTO> crear(@RequestBody ComentarioRequestDTO dto) {
        return ResponseEntity.ok(new ComentarioResponseDTO()); // lógica después
    }

    @GetMapping("/alojamiento/{alojamientoId}")
    @Operation(summary = "Listar comentarios por alojamiento", description = "Obtiene los comentarios de un alojamiento")
    public ResponseEntity<List<ComentarioResponseDTO>> listarPorAlojamiento(@PathVariable Long alojamientoId) {
        return ResponseEntity.ok(List.of());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar comentario", description = "Elimina un comentario por ID")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        return ResponseEntity.noContent().build();
    }
}
