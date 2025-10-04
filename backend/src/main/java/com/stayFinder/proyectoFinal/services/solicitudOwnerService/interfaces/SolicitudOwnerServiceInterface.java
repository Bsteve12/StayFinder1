package com.stayFinder.proyectoFinal.services.solicitudOwnerService.interfaces;

import com.stayFinder.proyectoFinal.dto.inputDTO.SolicitudOwnerDTO;
import com.stayFinder.proyectoFinal.dto.inputDTO.RespuestaSolicitudDTO;
import com.stayFinder.proyectoFinal.dto.outputDTO.SolicitudOwnerResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SolicitudOwnerServiceInterface {
    SolicitudOwnerResponseDTO crearSolicitud(SolicitudOwnerDTO dto, MultipartFile documento) throws Exception;
    SolicitudOwnerResponseDTO responderSolicitud(RespuestaSolicitudDTO dto) throws Exception;
    List<SolicitudOwnerResponseDTO> listarSolicitudesPendientes() throws Exception;
}
