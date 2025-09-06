package com.stayFinder.proyectoFinal.services.solicitudOwnerService.implementations;


import com.stayFinder.proyectoFinal.dto.SolicitudOwnerDTO;
import com.stayFinder.proyectoFinal.dto.RespuestaSolicitudDTO;
import com.stayFinder.proyectoFinal.entity.*;
import com.stayFinder.proyectoFinal.entity.enums.EstadoSolicitud;
import com.stayFinder.proyectoFinal.entity.enums.Role;
import com.stayFinder.proyectoFinal.repository.SolicitudOwnerRepository;
import com.stayFinder.proyectoFinal.repository.UsuarioRepository;

import com.stayFinder.proyectoFinal.services.solicitudOwnerService.interfaces.SolicitudOwnerServiceInterface;
import org.springframework.stereotype.Service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SolicitudOwnerServiceImpl implements SolicitudOwnerServiceInterface {

    private final SolicitudOwnerRepository solicitudRepo;
    private final UsuarioRepository usuarioRepo;

    public SolicitudOwnerServiceImpl(SolicitudOwnerRepository solicitudRepo, UsuarioRepository usuarioRepo) {
        this.solicitudRepo = solicitudRepo;
        this.usuarioRepo = usuarioRepo;
    }

    @Override
    public SolicitudOwner crearSolicitud(SolicitudOwnerDTO dto) {
        Usuario usuario = usuarioRepo.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        SolicitudOwner solicitud = new SolicitudOwner();
        solicitud.setUsuario(usuario);
        solicitud.setComentario(dto.getComentario());
        solicitud.setEstado(EstadoSolicitud.PENDIENTE);

        return solicitudRepo.save(solicitud);
    }

    @Override
    public SolicitudOwner responderSolicitud(RespuestaSolicitudDTO dto) {
        SolicitudOwner solicitud = solicitudRepo.findById(dto.getSolicitudId())
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        Usuario admin = usuarioRepo.findById(dto.getAdminId())
                .orElseThrow(() -> new RuntimeException("Admin no encontrado"));

        solicitud.setAdminRevisor(admin);
        solicitud.setFechaRevision(LocalDateTime.now());
        solicitud.setComentario(dto.getComentario());

        if (dto.isAprobada()) {
            solicitud.setEstado(EstadoSolicitud.APROBADA);

            // Cambiar el rol del usuario a OWNER
            Usuario usuario = solicitud.getUsuario();
            usuario.setRole(Role.OWNER); // Asumiendo que tienes un enum Role
            usuarioRepo.save(usuario);

        } else {
            solicitud.setEstado(EstadoSolicitud.RECHAZADA);
        }

        return solicitudRepo.save(solicitud);
    }

    @Override
    public List<SolicitudOwner> listarSolicitudesPendientes() {
        return solicitudRepo.findByEstado("PENDIENTE");
    }
}

