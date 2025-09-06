package com.stayFinder.proyectoFinal.services.userService.implementations;


import com.stayFinder.proyectoFinal.dto.CreateUserDTO;
import com.stayFinder.proyectoFinal.dto.LoginRequestDTO;
import com.stayFinder.proyectoFinal.dto.LoginResponse;
import com.stayFinder.proyectoFinal.dto.UpdateUserDTO;
import com.stayFinder.proyectoFinal.entity.Usuario;
import com.stayFinder.proyectoFinal.entity.enums.Role;
import com.stayFinder.proyectoFinal.repository.UsuarioRepository;
import com.stayFinder.proyectoFinal.security.JWTUtil;

import com.stayFinder.proyectoFinal.services.userService.interfaces.UserServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserServiceInterface {

    private final UsuarioRepository usuarioRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;

    // Crear un nuevo usuario
    @Override
    public void createUser(CreateUserDTO createUserDTO) throws Exception {
        if (usuarioRepository.existsByEmail(createUserDTO.correo())) {
            throw new Exception("El email ya existe");
        }

        String encodedPassword = passwordEncoder.encode(createUserDTO.contrasena());

        Usuario usuario = Usuario.builder()
                .nombre(createUserDTO.nombre())
                .email(createUserDTO.correo())
                .fechaNacimiento(createUserDTO.fechaNacimiento())
                .telefono(createUserDTO.telefono())
                .contrasena(encodedPassword)
                .role(Role.CLIENT) // siempre inicia como CLIENT
                .build();

        usuarioRepository.save(usuario);
    }

    // Login de usuario
    @Override
    public LoginResponse login(LoginRequestDTO loginRequestDTO) throws Exception {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDTO.email(), loginRequestDTO.contrasena()
                )
        );

        if (authentication.isAuthenticated()) {
            Optional<Usuario> user = usuarioRepository.findByEmail(authentication.getName());
            if (user.isEmpty()) throw new Exception("Usuario no existe");

            Usuario usuarioObject = user.get();
            String token = jwtUtil.GenerateToken(
                    usuarioObject.getId(),
                    usuarioObject.getEmail(),
                    usuarioObject.getRole()
            );

            return new LoginResponse(token);
        }
        throw new Exception("No se puede hacer login");
    }

    // Actualizar usuario
    @Override
    public void updateUser(UpdateUserDTO updateUserDTO) {
        Usuario usuario = usuarioRepository.findByEmail(updateUserDTO.email())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setNombre(updateUserDTO.nombre());
        usuario.setTelefono(updateUserDTO.telefono());
        usuario.setFechaNacimiento(updateUserDTO.fechaNacimiento());

        usuarioRepository.save(usuario);
    }

    // CRUD básico
    @Override
    public Usuario findByEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    @Override
    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }
}

