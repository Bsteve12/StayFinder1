package com.stayFinder.proyectoFinal.controller;

import com.stayFinder.proyectoFinal.dto.SolicitudOwnerDTO;
import com.stayFinder.proyectoFinal.dto.RespuestaSolicitudDTO;
import com.stayFinder.proyectoFinal.entity.SolicitudOwner;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.stayFinder.proyectoFinal.services.solicitudOwnerService.interfaces.SolicitudOwnerServiceInterface;
import java.util.List;

@RestController
@RequestMapping("/api/solicitudes-owner")
@CrossOrigin(origins = "http://localhost:4200")
public class SolicitudOwnerController {

    private final SolicitudOwnerServiceInterface solicitudService;

    public SolicitudOwnerController(SolicitudOwnerServiceInterface solicitudService) {
        this.solicitudService = solicitudService;
    }

    @PostMapping
    public ResponseEntity<SolicitudOwner> crearSolicitud(@RequestBody SolicitudOwnerDTO dto) {
        return ResponseEntity.ok(solicitudService.crearSolicitud(dto));
    }

    @PostMapping("/responder")
    public ResponseEntity<SolicitudOwner> responderSolicitud(@RequestBody RespuestaSolicitudDTO dto) {
        return ResponseEntity.ok(solicitudService.responderSolicitud(dto));
    }

    @GetMapping("/pendientes")
    public ResponseEntity<List<SolicitudOwner>> listarPendientes() {
        return ResponseEntity.ok(solicitudService.listarSolicitudesPendientes());
    }
}
