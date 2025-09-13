package com.stayFinder.proyectoFinal.controller;

import com.stayFinder.proyectoFinal.dto.inputDTO.MensajeInputDTO;
import com.stayFinder.proyectoFinal.dto.outputDTO.MensajeOutputDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/mensajes")
public class MensajeController {

    @GetMapping
    @Operation(summary = "Listar mensajes")
    @ApiResponse(responseCode = "200", description = "Listado de mensajes")
    public List<MensajeOutputDTO> listarMensajes() {
        return List.of(
                new MensajeOutputDTO(1L, 1L, 1L, "Hola", LocalDateTime.now()),
                new MensajeOutputDTO(2L, 1L, 2L, "Hola, ¿cómo estás?", LocalDateTime.now())
        );
    }

    @PostMapping
    @Operation(summary = "Enviar un mensaje")
    @ApiResponse(responseCode = "201", description = "Mensaje enviado")
    public MensajeOutputDTO enviarMensaje(@RequestBody MensajeInputDTO dto) {
        return new MensajeOutputDTO(99L, dto.getChatId(), dto.getRemitenteId(), dto.getContenido(), LocalDateTime.now());
    }
}
