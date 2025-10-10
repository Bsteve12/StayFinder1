package com.stayFinder.proyectoFinal.services.userService.implementations;

import com.stayFinder.proyectoFinal.dto.inputDTO.CreateUserDTO;
import com.stayFinder.proyectoFinal.dto.inputDTO.LoginRequestDTO;
import com.stayFinder.proyectoFinal.dto.inputDTO.UpdateUserDTO;
import com.stayFinder.proyectoFinal.dto.outputDTO.LoginResponseDTO;
import com.stayFinder.proyectoFinal.dto.outputDTO.UsuarioResponseDTO;
import com.stayFinder.proyectoFinal.entity.Usuario;
import com.stayFinder.proyectoFinal.entity.enums.Role;
import com.stayFinder.proyectoFinal.mapper.UsuarioMapper;
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
    private final UsuarioMapper usuarioMapper;

    // Crear usuario (CLIENT por defecto o cualquier rol si lo hace ADMIN)
    @Override
    public UsuarioResponseDTO createUser(CreateUserDTO dto, Role roleSolicitado, Long adminId) throws Exception {
        if (usuarioRepository.existsByEmail(dto.correo())) {
            throw new Exception("El email ya existe");
        }

        Role rolFinal = Role.CLIENT; // por defecto

        if (roleSolicitado != null) {
            if (adminId == null) throw new Exception("Solo un ADMIN puede asignar roles");
            Usuario admin = usuarioRepository.findById(adminId)
                    .orElseThrow(() -> new Exception("Admin no encontrado"));
            if (admin.getRole() != Role.ADMIN) throw new Exception("No autorizado para asignar roles");
            rolFinal = roleSolicitado;
        }

        String encodedPassword = passwordEncoder.encode(dto.contrasena());

        Usuario usuario = Usuario.builder()
                .nombre(dto.nombre())
                .email(dto.correo())
                .fechaNacimiento(dto.fechaNacimiento())
                .telefono(dto.telefono())
                .contrasena(encodedPassword)
                .usuario_id(dto.usuario_id())
                .role(rolFinal)
                .build();

        return usuarioMapper.toDto(usuarioRepository.save(usuario));
    }

    // Login de usuario
    @Override
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) throws Exception {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDTO.email(), loginRequestDTO.contrasena()
                )
        );

        if (authentication.isAuthenticated()) {
            Optional<Usuario> user = usuarioRepository.findByEmail(authentication.getName());
            if (user.isEmpty()) throw new Exception("Usuario no existe");

            Usuario usuario = user.get();
            String token = jwtUtil.GenerateToken(
                    usuario.getId(),
                    usuario.getEmail(),
                    usuario.getRole()
            );

            return new LoginResponseDTO(token);
        }
        throw new Exception("Credenciales inválidas");
    }

    // Actualizar usuario (solo self o admin)
    @Override
    public UsuarioResponseDTO updateUser(Long id, UpdateUserDTO dto, Long actorId) throws Exception {
        Usuario actor = usuarioRepository.findById(actorId)
                .orElseThrow(() -> new Exception("Usuario actor no encontrado"));
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new Exception("Usuario no encontrado"));

        if (!actor.getId().equals(usuario.getId()) && actor.getRole() != Role.ADMIN) {
            throw new Exception("No tienes permisos para actualizar este usuario");
        }

        usuario.setNombre(dto.nombre());
        usuario.setTelefono(dto.telefono());
        usuario.setFechaNacimiento(dto.fechaNacimiento());
        usuario.setUsuario_id(dto.usuario_id());

        if (dto.contrasena() != null && !dto.contrasena().isBlank()) {
            usuario.setContrasena(passwordEncoder.encode(dto.contrasena()));
        }

        return usuarioMapper.toDto(usuarioRepository.save(usuario));
    }

    // Eliminar usuario
    @Override
    public void deleteUser(Long id, Long actorId) throws Exception {
        Usuario actor = usuarioRepository.findById(actorId)
                .orElseThrow(() -> new Exception("Usuario actor no encontrado"));
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new Exception("Usuario no encontrado"));

        if (!actor.getId().equals(usuario.getId()) && actor.getRole() != Role.ADMIN) {
            throw new Exception("No tienes permisos para eliminar este usuario");
        }

        usuarioRepository.delete(usuario);
    }

    // Asignar rol
    @Override
    public UsuarioResponseDTO assignRole(Long userId, Role newRole, Long adminId) throws Exception {
        Usuario admin = usuarioRepository.findById(adminId)
                .orElseThrow(() -> new Exception("Admin no encontrado"));
        if (admin.getRole() != Role.ADMIN) throw new Exception("No autorizado para asignar roles");

        Usuario usuario = usuarioRepository.findById(userId)
                .orElseThrow(() -> new Exception("Usuario no encontrado"));

        usuario.setRole(newRole);
        return usuarioMapper.toDto(usuarioRepository.save(usuario));
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
    public List<UsuarioResponseDTO> findAll() {
        return usuarioRepository.findAll().stream()
                .map(usuarioMapper::toDto)
                .toList();
    }

    @Override
    public List<UsuarioResponseDTO> getUsuariosPorRol(String role) {
        return usuarioRepository.buscarUsuariosPorRol(role).stream()
                .map(usuarioMapper::toDto)
                .toList();
    }
}
