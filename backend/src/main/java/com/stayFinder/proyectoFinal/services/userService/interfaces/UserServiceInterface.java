package com.stayFinder.proyectoFinal.services.userService.interfaces;



import java.util.List;

import com.stayFinder.proyectoFinal.dto.inputDTO.CreateUserDTO;
import com.stayFinder.proyectoFinal.dto.inputDTO.LoginRequestDTO;
import com.stayFinder.proyectoFinal.dto.outputDTO.LoginResponseDTO;
import com.stayFinder.proyectoFinal.dto.inputDTO.UpdateUserDTO;
import com.stayFinder.proyectoFinal.entity.Usuario;

public interface UserServiceInterface {

    // Manejo de usuarios
    void createUser(CreateUserDTO createUserDTO) throws Exception;
    void updateUser(UpdateUserDTO updateUserDTO);

    // Autenticación
    LoginResponseDTO login(LoginRequestDTO loginRequestDTO) throws Exception;

    // CRUD básico de Usuario
    Usuario findByEmail(String email);
    Usuario save(Usuario usuario);
    List<Usuario> findAll();
}
