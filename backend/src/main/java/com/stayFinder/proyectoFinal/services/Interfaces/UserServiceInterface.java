package com.stayFinder.proyectoFinal.services.Interfaces;

import com.stayFinder.proyectoFinal.dto.CreateUserDTO;
import com.stayFinder.proyectoFinal.dto.LoginRequestDTO;
import com.stayFinder.proyectoFinal.dto.LoginResponse;
import com.stayFinder.proyectoFinal.dto.UpdateUserDTO;

public interface UserServiceInterface {

    void createUser(CreateUserDTO createUserDTO) throws Exception;
    void updateUser (UpdateUserDTO updateUserDTO);
    LoginResponse login(LoginRequestDTO loginRequestDTO) throws Exception;
}
