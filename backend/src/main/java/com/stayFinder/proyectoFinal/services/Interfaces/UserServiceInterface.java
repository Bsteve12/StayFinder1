package com.stayFinder.proyectoFinal.services.Interfaces;

import java.util.List;

import com.stayFinder.proyectoFinal.dto.CreateUserDTO;
import com.stayFinder.proyectoFinal.dto.LoginRequestDTO;
import com.stayFinder.proyectoFinal.dto.LoginResponse;
import com.stayFinder.proyectoFinal.dto.UpdateUserDTO;
import com.stayFinder.proyectoFinal.entity.Usuario;

public interface UserServiceInterface {

    // Manejo de usuarios
    void createUser(CreateUserDTO createUserDTO) throws Exception;
    void updateUser(UpdateUserDTO updateUserDTO);

    // Autenticación
    LoginResponse login(LoginRequestDTO loginRequestDTO) throws Exception;

    // CRUD básico de Usuario
    Usuario findByEmail(String email);
    Usuario save(Usuario usuario);
    List<Usuario> findAll();
}
