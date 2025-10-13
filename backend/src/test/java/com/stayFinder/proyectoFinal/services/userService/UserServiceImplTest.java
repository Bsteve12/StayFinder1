package com.stayFinder.proyectoFinal.services.userService;

import com.stayFinder.proyectoFinal.dto.inputDTO.CreateUserDTO;
import com.stayFinder.proyectoFinal.dto.inputDTO.LoginRequestDTO;
import com.stayFinder.proyectoFinal.dto.outputDTO.UsuarioResponseDTO;
import com.stayFinder.proyectoFinal.entity.Usuario;
import com.stayFinder.proyectoFinal.entity.enums.Role;
import com.stayFinder.proyectoFinal.mapper.UsuarioMapper;
import com.stayFinder.proyectoFinal.repository.UsuarioRepository;
import com.stayFinder.proyectoFinal.security.JWTUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UsuarioMapper usuarioMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private JWTUtil jwtUtil;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtUtil = new JWTUtil(); // Clave secreta para pruebas
        userService.setJwtUtil(jwtUtil); // Asegúrate de que UserServiceImpl permita inyectar JWTUtil
    }

    @Test
    void testCreateUser_Success() throws Exception {
        // Arrange
        CreateUserDTO dto = new CreateUserDTO(
                "Allison", "alli@example.com", "3001234567",
                "2004-05-10", "contrasena", 100L
        );
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setRole(Role.CLIENT);

        when(usuarioRepository.existsByEmail(dto.correo())).thenReturn(false);
        when(usuarioRepository.existsByUsuarioId(dto.usuario_id())).thenReturn(false);
        when(passwordEncoder.encode(dto.contrasena())).thenReturn("encodedPassword");
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);
        when(usuarioMapper.toDto(any(Usuario.class))).thenReturn(new UsuarioResponseDTO(
                1L,
                "Allison",
                "alli@example.com",
                "3001234567",
                LocalDate.parse("2004-05-10"),
                100L,
                Role.CLIENT
        ));

        // Act
        UsuarioResponseDTO result = userService.createUser(dto, null, null);

        // Assert
        assertNotNull(result);
        assertEquals("Allison", result.nombre());
        verify(usuarioRepository, times(1)).existsByEmail(dto.correo());
        verify(usuarioRepository, times(1)).existsByUsuarioId(dto.usuario_id());
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    void testCreateUser_EmailAlreadyExists() {
        // Arrange
        CreateUserDTO dto = new CreateUserDTO(
                "Allison", "alli@example.com", "3001234567",
                "2004-05-10", "contrasena", 100L
        );

        when(usuarioRepository.existsByEmail(dto.correo())).thenReturn(true);

        // Act & Assert
        Exception exception = assertThrows(Exception.class, () -> userService.createUser(dto, null, null));
        assertEquals("El email ya existe", exception.getMessage());
        verify(usuarioRepository, times(1)).existsByEmail(dto.correo());
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    void testLogin_InvalidCredentials() {
        // Arrange
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO("john@example.com", "wrongPassword");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenThrow(new RuntimeException("Credenciales inválidas"));

        // Act & Assert
        Exception exception = assertThrows(Exception.class, () -> userService.login(loginRequestDTO));
        assertEquals("Credenciales inválidas", exception.getMessage());
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }
}