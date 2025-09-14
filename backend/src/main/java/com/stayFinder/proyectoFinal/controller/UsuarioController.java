package com.stayFinder.proyectoFinal.controller;

import com.stayFinder.proyectoFinal.dto.inputDTO.CreateUserDTO;
import com.stayFinder.proyectoFinal.dto.inputDTO.LoginRequestDTO;
import com.stayFinder.proyectoFinal.dto.inputDTO.UpdateUserDTO;
import com.stayFinder.proyectoFinal.dto.outputDTO.ControllerGeneralResponseDTO;
import com.stayFinder.proyectoFinal.dto.outputDTO.LoginResponseDTO;
import com.stayFinder.proyectoFinal.entity.Usuario;
import com.stayFinder.proyectoFinal.services.userService.interfaces.UserServiceInterface;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/usuario")
@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "Usuarios", description = "Operaciones de usuario (registro, login, actualización)")
public class UsuarioController {

    private final UserServiceInterface userServiceInterface;

    @PostMapping("/login")
    @Operation(summary = "Login (DTO)", description = "Autentica usuario y devuelve token o datos (LoginResponseDTO).")
    public ResponseEntity<LoginResponseDTO> login(
            @Valid @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "LoginRequestDTO (email y contraseña)", required = true)
            @RequestBody LoginRequestDTO loginDTO) throws Exception {
        LoginResponseDTO responseDTO = userServiceInterface.login(loginDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping
    @Operation(summary = "Crear usuario", description = "Crea un usuario nuevo con CreateUserDTO.")
    public ResponseEntity<ControllerGeneralResponseDTO> createUser(
            @Valid @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "CreateUserDTO con datos del usuario", required = true)
            @RequestBody CreateUserDTO createUserDTO) throws Exception {
        userServiceInterface.createUser(createUserDTO);
        return ResponseEntity.ok().body(new ControllerGeneralResponseDTO(true, "Usuario creado correctamente"));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar perfil de usuario",
            description = "Permite al usuario actualizar su información personal (nombre, teléfono, fechaNacimiento, contraseña).")
    public ResponseEntity<ControllerGeneralResponseDTO> updateUser(
            @PathVariable Long id,
            @Valid @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "UpdateUserDTO con los nuevos datos del usuario", required = true)
            @RequestBody UpdateUserDTO updateUserDTO) {

        userServiceInterface.updateUser(id, updateUserDTO);
        return ResponseEntity.ok(new ControllerGeneralResponseDTO(true, "Usuario actualizado correctamente"));
    }
}
