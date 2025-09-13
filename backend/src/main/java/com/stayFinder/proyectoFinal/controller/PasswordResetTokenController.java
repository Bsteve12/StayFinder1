package com.stayFinder.proyectoFinal.controller;

import com.stayFinder.proyectoFinal.dto.inputDTO.PasswordResetTokenInputDTO;
import com.stayFinder.proyectoFinal.dto.outputDTO.PasswordResetTokenOutputDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/password-tokens")
public class PasswordResetTokenController {

    @GetMapping
    @Operation(summary = "Listar tokens de recuperación de contraseña")
    @ApiResponse(responseCode = "200", description = "Listado de tokens")
    public List<PasswordResetTokenOutputDTO> listarTokens() {
        return List.of(
                new PasswordResetTokenOutputDTO(1L, 1L, "token123", LocalDateTime.now().plusMinutes(15), false),
                new PasswordResetTokenOutputDTO(2L, 2L, "token456", LocalDateTime.now().plusMinutes(10), true)
        );
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo token de recuperación")
    @ApiResponse(responseCode = "201", description = "Token creado")
    public PasswordResetTokenOutputDTO crearToken(@RequestBody PasswordResetTokenInputDTO dto) {
        return new PasswordResetTokenOutputDTO(
                99L, dto.getUsuarioId(), dto.getToken(), LocalDateTime.now().plusMinutes(15), false
        );
    }
}
