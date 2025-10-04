package com.stayFinder.proyectoFinal.mapper;

import com.stayFinder.proyectoFinal.dto.inputDTO.ComentarioRequestDTO;
import com.stayFinder.proyectoFinal.dto.outputDTO.ComentarioResponseDTO;
import com.stayFinder.proyectoFinal.entity.Comentario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ComentarioMapper {

    // De entidad a DTO
    @Mapping(target = "nombreUsuario", source = "usuario.nombre")       // <-- nombre correcto
    @Mapping(target = "mensaje", source = "mensaje")
    @Mapping(target = "calificacion", source = "calificacion")
    @Mapping(target = "respuestaAnfitrion", source = "respuestaAnfitrion")
    @Mapping(target = "nombreAnfitrion", source = "alojamiento.owner.nombre")
    @Mapping(target = "fechaCreacion", source = "fechaCreacion")
    ComentarioResponseDTO toDto(Comentario comentario);

    // De DTO a entidad
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "alojamiento", ignore = true)
    @Mapping(target = "reserva", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "respuestaAnfitrion", ignore = true)
    Comentario toEntity(ComentarioRequestDTO dto);
}
