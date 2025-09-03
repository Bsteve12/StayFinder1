package com.stayFinder.proyectoFinal.dto;

public record UpdateUserDTO(String nombre, String telefono, String fechaNacimiento, String contrasena) {

    public String email() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'email'");
    }
    
}
