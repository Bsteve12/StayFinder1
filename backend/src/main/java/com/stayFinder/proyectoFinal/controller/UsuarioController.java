package com.stayFinder.proyectoFinal.controller;
import com.stayFinder.proyectoFinal.dto.ControllerGeneralResponseDTO;
import com.stayFinder.proyectoFinal.dto.CreateUserDTO;
import com.stayFinder.proyectoFinal.dto.LoginRequestDTO;
import com.stayFinder.proyectoFinal.dto.LoginResponse;
import com.stayFinder.proyectoFinal.services.userService.interfaces.UserServiceInterface;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/usuario")
@CrossOrigin(origins = "http://localhost:4200")

public class UsuarioController {
  private final UserServiceInterface userServiceInterface;

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequestDTO loginDTO) throws Exception {
    LoginResponse responseDTO = userServiceInterface.login(loginDTO);
    return ResponseEntity.ok(responseDTO);
  }

  @PostMapping
  public ResponseEntity<ControllerGeneralResponseDTO> createUser(@Valid @RequestBody CreateUserDTO createUserDTO) throws Exception {
    userServiceInterface.createUser(createUserDTO);
    return ResponseEntity.ok().body(new ControllerGeneralResponseDTO(true, "Usuario creado correctamente"));
  }
}