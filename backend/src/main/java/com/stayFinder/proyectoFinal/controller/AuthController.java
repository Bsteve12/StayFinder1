package com.stayFinder.proyectoFinal.controller;

import com.stayFinder.proyectoFinal.entity.Usuario;
import com.stayFinder.proyectoFinal.services.UsuarioService;
import com.stayFinder.proyectoFinal.dto.LoginRequestDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final UsuarioService usuarioService;

    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/register")
    public Usuario register(@RequestBody Usuario usuario) {
        return usuarioService.save(usuario);
    }

    @PostMapping("/login")
    public Usuario login(@RequestBody LoginRequestDTO loginRequest) {
        Usuario u = usuarioService.findByEmail(loginRequest.email());
        if (u != null && u.getContrasena().equals(loginRequest.contrasena())) {
            return u;
        }
        return null;
    }

    @GetMapping("/all")
    public List<Usuario> allUsers() {
        return usuarioService.findAll();
    }
}
