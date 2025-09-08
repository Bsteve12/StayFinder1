package com.stayFinder.proyectoFinal.controller;

import com.stayFinder.proyectoFinal.dto.inputDTO.SolicitudOwnerDTO;
import com.stayFinder.proyectoFinal.dto.outputDTO.RespuestaSolicitudDTO;
import com.stayFinder.proyectoFinal.entity.SolicitudOwner;
import com.stayFinder.proyectoFinal.services.solicitudOwnerService.interfaces.SolicitudOwnerServiceInterface;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/solicitudes-owner")
@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "Solicitudes Owner", description = "Solicitudes para convertirse en owner y responderlas (admin)")
public class SolicitudOwnerController {

    private final SolicitudOwnerServiceInterface solicitudService;

    public SolicitudOwnerController(SolicitudOwnerServiceInterface solicitudService) {
        this.solicitudService = solicitudService;
    }

    @PostMapping
    @Operation(summary = "Crear solicitud para ser owner", description = "Envía la solicitud (con PDF u otros datos) para que el admin la revise.")
    public ResponseEntity<SolicitudOwner> crearSolicitud(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "DTO con los datos de la solicitud", required = true)
            @RequestBody SolicitudOwnerDTO dto) {
        return ResponseEntity.ok(solicitudService.crearSolicitud(dto));
    }

    @PostMapping("/responder")
    @Operation(summary = "Responder solicitud (admin)", description = "Admin responde la solicitud: aprueba o rechaza.")
    public ResponseEntity<SolicitudOwner> responderSolicitud(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "DTO con respuesta (aprobada o rechazada)", required = true)
            @RequestBody RespuestaSolicitudDTO dto) {
        return ResponseEntity.ok(solicitudService.responderSolicitud(dto));
    }

    @GetMapping("/pendientes")
    @Operation(summary = "Listar solicitudes pendientes", description = "Devuelve las solicitudes con estado PENDIENTE para revisión.")
    public ResponseEntity<List<SolicitudOwner>> listarPendientes() {
        return ResponseEntity.ok(solicitudService.listarSolicitudesPendientes());
    }
}
