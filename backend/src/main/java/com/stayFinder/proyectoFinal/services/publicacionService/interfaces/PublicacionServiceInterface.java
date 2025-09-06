package com.stayFinder.proyectoFinal.services.publicacionService.interfaces;


import com.stayFinder.proyectoFinal.dto.PublicacionRequestDTO;
import com.stayFinder.proyectoFinal.dto.PublicacionResponseDTO;

import java.util.List;

public interface PublicacionServiceInterface {

    PublicacionResponseDTO crearPublicacion(PublicacionRequestDTO dto);

    List<PublicacionResponseDTO> listarPendientes();

    PublicacionResponseDTO aprobarPublicacion(Long id);

    PublicacionResponseDTO rechazarPublicacion(Long id);
}
