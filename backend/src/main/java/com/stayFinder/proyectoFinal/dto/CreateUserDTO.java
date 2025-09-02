package com.stayFinder.proyectoFinal.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;

public record CreateUserDTO(
   @Length(max = 30) String nombre, @Email String correo, 
   String telefono,
   String fechaNacimiento,
   String contrasena) { // validaciones 

    

}
