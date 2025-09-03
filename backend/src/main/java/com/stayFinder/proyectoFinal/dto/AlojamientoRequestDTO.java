package com.stayFinder.proyectoFinal.dto;

public record AlojamientoRequestDTO(
        String nombre,
        String direccion,
        Double precio,
        String descripcion
) { }
