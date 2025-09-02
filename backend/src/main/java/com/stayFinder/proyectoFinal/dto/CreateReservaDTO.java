package com.stayFinder.proyectoFinal.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;

public record CreateReservaDTO(
    Long alojamientoId,
    String fecha

) { // validaciones 

    

}
