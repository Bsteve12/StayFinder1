package com.stayFinder.proyectoFinal.controller;

import com.stayFinder.proyectoFinal.dto.PublicacionRequestDTO;
import com.stayFinder.proyectoFinal.dto.PublicacionResponseDTO;
import com.stayFinder.proyectoFinal.services.publicacionService.interfaces.PublicacionServiceInterface;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/publicaciones")
public class PublicacionController {

    private final PublicacionServiceInterface publicacionService;

    public PublicacionController(PublicacionServiceInterface publicacionService) {
        this.publicacionService = publicacionService;
    }

    @PostMapping
    public PublicacionResponseDTO crear(@RequestBody PublicacionRequestDTO dto) {
        return publicacionService.crearPublicacion(dto);
    }

    @GetMapping("/pendientes")
    public List<PublicacionResponseDTO> listarPendientes() {
        return publicacionService.listarPendientes();
    }

    @PutMapping("/{id}/aprobar")
    public PublicacionResponseDTO aprobar(@PathVariable Long id) {
        return publicacionService.aprobarPublicacion(id);
    }

    @PutMapping("/{id}/rechazar")
    public PublicacionResponseDTO rechazar(@PathVariable Long id) {
        return publicacionService.rechazarPublicacion(id);
    }
}
