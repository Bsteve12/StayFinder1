package com.stayFinder.proyectoFinal.services.implementations;

import org.springframework.stereotype.Service;

import com.stayFinder.proyectoFinal.dto.CreateUserDTO;
import com.stayFinder.proyectoFinal.dto.LoginRequestDTO;
import com.stayFinder.proyectoFinal.dto.LoginResponse;
import com.stayFinder.proyectoFinal.dto.UpdateUserDTO;
import com.stayFinder.proyectoFinal.entity.Usuario;
import com.stayFinder.proyectoFinal.entity.enums.Role;
import com.stayFinder.proyectoFinal.repository.UsuarioRepository;
import com.stayFinder.proyectoFinal.security.JWTUtil;
import com.stayFinder.proyectoFinal.services.Interfaces.UserServiceInterface;
import lombok.RequiredArgsConstructor;
import java.util.Optional;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Service
@RequiredArgsConstructor

public class UserServiceImpl implements  UserServiceInterface{
    
    private final UsuarioRepository usuarioRepository;
    private final AuthenticationManager authenticationManager; 
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;
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
                .role(Role.CLIENT)
                .build();

        usuarioRepository.save(usuario);

}

    @Override
    public LoginResponse login(LoginRequestDTO loginRequestDTO) throws Exception {
        // TODO Auto-generated method stub
       
         Authentication authentication = authenticationManager.
         authenticate(new UsernamePasswordAuthenticationToken(loginRequestDTO.email(), loginRequestDTO.contrasena()));
         if (authentication.isAuthenticated()){
            
            Optional<Usuario> user = usuarioRepository.findByEmail(authentication.getName());
            if (user.isEmpty()) throw new Exception("Usuario no existe ");

            Usuario usuarioObject = user.get();
            String token = jwtUtil.GenerateToken(usuarioObject.getId(), usuarioObject.getEmail(), usuarioObject.getRole());
           
            return new LoginResponse(token);
        
        }
        throw new Exception("No se puede hacer login");
    }

    @Override
    public void updateUser(UpdateUserDTO updateUserDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateUser'");
    }
      
}