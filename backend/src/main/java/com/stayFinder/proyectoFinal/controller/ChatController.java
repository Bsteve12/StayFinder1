package com.stayFinder.proyectoFinal.controller;

import com.stayFinder.proyectoFinal.dto.inputDTO.ChatInputDTO;
import com.stayFinder.proyectoFinal.dto.outputDTO.ChatOutputDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chats")
public class ChatController {

    @GetMapping
    @Operation(summary = "Listar todos los chats")
    @ApiResponse(responseCode = "200", description = "Listado de chats")
    public List<ChatOutputDTO> listarChats() {
        return List.of(
                new ChatOutputDTO(1L, 1L, 2L),
                new ChatOutputDTO(2L, 3L, 4L)
        );
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo chat")
    @ApiResponse(responseCode = "201", description = "Chat creado")
    public ChatOutputDTO crearChat(@RequestBody ChatInputDTO dto) {
        return new ChatOutputDTO(99L, dto.getUsuarioId(), dto.getAnfitrionId());
    }
}
