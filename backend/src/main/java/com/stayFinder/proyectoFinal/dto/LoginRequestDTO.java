package com.stayFinder.proyectoFinal.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record LoginRequestDTO(
    @Email @NotNull String email, 
    @NotNull String contrasena) {
}
