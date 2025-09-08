package com.stayFinder.proyectoFinal.services.publicacionService.implementations;


import com.stayFinder.proyectoFinal.dto.inputDTO.PublicacionRequestDTO;
import com.stayFinder.proyectoFinal.dto.outputDTO.PublicacionResponseDTO;
import com.stayFinder.proyectoFinal.entity.*;
import com.stayFinder.proyectoFinal.entity.enums.EstadoPublicacion;
import com.stayFinder.proyectoFinal.entity.enums.Role;
import com.stayFinder.proyectoFinal.repository.PublicacionRepository;
import com.stayFinder.proyectoFinal.repository.UsuarioRepository;
import com.stayFinder.proyectoFinal.services.publicacionService.interfaces.PublicacionServiceInterface;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PublicacionServiceImpl implements PublicacionServiceInterface {

    private final PublicacionRepository publicacionRepository;
    private final UsuarioRepository usuarioRepository;

    public PublicacionServiceImpl(PublicacionRepository publicacionRepository,
                                  UsuarioRepository usuarioRepository) {
        this.publicacionRepository = publicacionRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public PublicacionResponseDTO crearPublicacion(PublicacionRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Publicacion publicacion = new Publicacion();
        publicacion.setTitulo(dto.getTitulo());
        publicacion.setDescripcion(dto.getDescripcion());
        publicacion.setUsuario(usuario);

        // Si es admin -> aprobado directo, si no -> pendiente
        if (usuario.getEmail().equals(Role.ADMIN)) {
            publicacion.setEstado(EstadoPublicacion.APROBADA);
        } else {
            publicacion.setEstado(EstadoPublicacion.PENDIENTE);
        }

        return mapToDTO(publicacionRepository.save(publicacion));
    }

    @Override
    public List<PublicacionResponseDTO> listarPendientes() {
        return publicacionRepository.findByEstado(EstadoPublicacion.PENDIENTE)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public PublicacionResponseDTO aprobarPublicacion(Long id) {
        Publicacion publicacion = publicacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Publicación no encontrada"));
        publicacion.setEstado(EstadoPublicacion.APROBADA);
        return mapToDTO(publicacionRepository.save(publicacion));
    }

    public PublicacionResponseDTO rechazarPublicacion(Long id) {
        Publicacion publicacion = publicacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Publicación no encontrada"));
        publicacion.setEstado(EstadoPublicacion.RECHAZADA);
        return mapToDTO(publicacionRepository.save(publicacion));
    }

    private PublicacionResponseDTO mapToDTO(Publicacion publicacion) {
        PublicacionResponseDTO dto = new PublicacionResponseDTO();
        dto.setId(publicacion.getId());
        dto.setTitulo(publicacion.getTitulo());
        dto.setDescripcion(publicacion.getDescripcion());
        dto.setEstado(publicacion.getEstado());
        dto.setNombreUsuario(publicacion.getUsuario().getNombre());
        return dto;
    }
}
