package com.stayFinder.proyectoFinal.services.Interfaces;

import com.stayFinder.proyectoFinal.dto.SolicitudOwnerDTO;
import com.stayFinder.proyectoFinal.dto.RespuestaSolicitudDTO;
import com.stayFinder.proyectoFinal.entity.SolicitudOwner;

import java.util.List;

public interface SolicitudOwnerServiceInterface {
    SolicitudOwner crearSolicitud(SolicitudOwnerDTO dto);
    SolicitudOwner responderSolicitud(RespuestaSolicitudDTO dto);
    List<SolicitudOwner> listarSolicitudesPendientes();
}
