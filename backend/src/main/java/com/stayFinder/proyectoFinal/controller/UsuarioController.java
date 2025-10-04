package com.stayFinder.proyectoFinal.controller;

import com.stayFinder.proyectoFinal.dto.inputDTO.CreateUserDTO;
import com.stayFinder.proyectoFinal.dto.inputDTO.LoginRequestDTO;
import com.stayFinder.proyectoFinal.dto.inputDTO.UpdateUserDTO;
import com.stayFinder.proyectoFinal.dto.outputDTO.ControllerGeneralResponseDTO;
import com.stayFinder.proyectoFinal.dto.outputDTO.LoginResponseDTO;
import com.stayFinder.proyectoFinal.dto.outputDTO.UsuarioResponseDTO;
import com.stayFinder.proyectoFinal.entity.enums.Role;
import com.stayFinder.proyectoFinal.services.userService.interfaces.UserServiceInterface;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/usuario")
@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "Usuarios", description = "Operaciones de usuario (registro, login, actualización, roles)")
public class UsuarioController {

    private final UserServiceInterface userService;

    @PostMapping("/login")
    @Operation(summary = "Login de usuario", description = "Autentica usuario y devuelve token JWT.")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginDTO) throws Exception {
        return ResponseEntity.ok(userService.login(loginDTO));
    }

    @PostMapping
    @Operation(summary = "Crear usuario", description = "Crea un usuario. Si lo hace un admin puede asignar rol inicial.")
    public ResponseEntity<UsuarioResponseDTO> createUser(
            @Valid @RequestBody CreateUserDTO dto,
            @RequestParam(required = false) Role role, // opcional: solo admin puede usarlo
            @RequestParam(required = false) Long adminId
    ) throws Exception {
        return ResponseEntity.ok(userService.createUser(dto, role, adminId));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar usuario", description = "El propio usuario o un admin puede actualizarlo.")
    public ResponseEntity<UsuarioResponseDTO> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserDTO dto,
            @RequestParam Long actorId // quien ejecuta la acción
    ) throws Exception {
        return ResponseEntity.ok(userService.updateUser(id, dto, actorId));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar usuario", description = "El propio usuario o un admin puede eliminarlo.")
    public ResponseEntity<ControllerGeneralResponseDTO> deleteUser(
            @PathVariable Long id,
            @RequestParam Long actorId
    ) throws Exception {
        userService.deleteUser(id, actorId);
        return ResponseEntity.ok(new ControllerGeneralResponseDTO(true, "Usuario eliminado correctamente"));
    }

    @PutMapping("/{id}/role")
    @Operation(summary = "Asignar rol", description = "Solo un admin puede asignar rol a otro usuario.")
    public ResponseEntity<UsuarioResponseDTO> assignRole(
            @PathVariable Long id,
            @RequestParam Role newRole,
            @RequestParam Long adminId
    ) throws Exception {
        return ResponseEntity.ok(userService.assignRole(id, newRole, adminId));
    }

    @GetMapping
    @Operation(summary = "Listar usuarios", description = "Devuelve todos los usuarios registrados.")
    public ResponseEntity<List<UsuarioResponseDTO>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/rol/{rol}")
    @Operation(summary = "Listar usuarios por rol", description = "Filtra usuarios por rol (CLIENT, OWNER, ADMIN).")
    public ResponseEntity<List<UsuarioResponseDTO>> getByRol(@PathVariable String rol) {
        return ResponseEntity.ok(userService.getUsuariosPorRol(rol));
    }
}
