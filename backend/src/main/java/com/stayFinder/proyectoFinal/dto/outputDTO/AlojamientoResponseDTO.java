package com.stayFinder.proyectoFinal.dto.outputDTO;


public record AlojamientoResponseDTO(
        Long id,
        String nombre,
        String direccion,
        Double precio,
        String descripcion,
        Long ownerId
) { }