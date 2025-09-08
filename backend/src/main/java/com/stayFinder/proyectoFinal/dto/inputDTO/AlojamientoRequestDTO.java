package com.stayFinder.proyectoFinal.dto.inputDTO;

public record AlojamientoRequestDTO(
        String nombre,
        String direccion,
        Double precio,
        String descripcion
) { }