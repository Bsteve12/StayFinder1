package com.stayFinder.proyectoFinal.mapper;

import com.stayFinder.proyectoFinal.dto.inputDTO.CreateUserDTO;
import com.stayFinder.proyectoFinal.dto.outputDTO.UsuarioResponseDTO;
import com.stayFinder.proyectoFinal.entity.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    // De CreateUserDTO -> Usuario (no mapeamos la contraseña, la maneja el service)
    @Mapping(source = "correo", target = "email")
    Usuario toEntity(CreateUserDTO dto);

    // De Usuario -> UsuarioResponseDTO (ajusta nombres si tu DTO tiene campos distintos)
    @Mapping(source = "email", target = "correo")
    UsuarioResponseDTO toDto(Usuario usuario);
}
