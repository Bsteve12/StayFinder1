package com.stayFinder.proyectoFinal.controller;

import com.stayFinder.proyectoFinal.dto.inputDTO.FavoriteRequestDTO;
import com.stayFinder.proyectoFinal.dto.outputDTO.FavoriteResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favoritos")
@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "Favoritos", description = "Gestión de alojamientos favoritos por usuario")
public class FavoriteController {

    @PostMapping
    @Operation(summary = "Agregar a favoritos", description = "Permite a un usuario marcar un alojamiento como favorito.")
    public ResponseEntity<FavoriteResponseDTO> agregar(@RequestBody FavoriteRequestDTO dto) {
        return ResponseEntity.ok(new FavoriteResponseDTO());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar favorito", description = "Quita un alojamiento de la lista de favoritos.")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Listar favoritos de un usuario", description = "Devuelve los alojamientos favoritos de un usuario.")
    public ResponseEntity<List<FavoriteResponseDTO>> listarPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(List.of());
    }
}
