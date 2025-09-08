package com.stayFinder.proyectoFinal.services.solicitudOwnerService.interfaces;


import com.stayFinder.proyectoFinal.dto.inputDTO.SolicitudOwnerDTO;
import com.stayFinder.proyectoFinal.dto.outputDTO.RespuestaSolicitudDTO;
import com.stayFinder.proyectoFinal.entity.SolicitudOwner;

import java.util.List;

public interface SolicitudOwnerServiceInterface {
    SolicitudOwner crearSolicitud(SolicitudOwnerDTO dto);
    SolicitudOwner responderSolicitud(RespuestaSolicitudDTO dto);
    List<SolicitudOwner> listarSolicitudesPendientes();
}

