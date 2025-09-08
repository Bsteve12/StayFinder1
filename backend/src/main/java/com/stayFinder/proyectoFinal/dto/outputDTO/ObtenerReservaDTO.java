package com.stayFinder.proyectoFinal.dto.outputDTO;


public record ObtenerReservaDTO(
        Long id,
        String fecha,
        Double precioTotal,
        String nombreAlojamiento,
        String nombreUsuario,
        String telefonoUsuario
) {

}