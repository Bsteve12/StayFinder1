package com.stayFinder.proyectoFinal.dto.inputDTO;


public record UpdateUserDTO(String nombre, String telefono, String fechaNacimiento, String contrasena) {

    public String email() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'email'");
    }

}
