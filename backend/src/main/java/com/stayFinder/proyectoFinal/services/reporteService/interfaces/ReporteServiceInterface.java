package com.stayFinder.proyectoFinal.services.reporteService.interfaces;

import com.stayFinder.proyectoFinal.dto.outputDTO.*;
import java.util.List;

public interface ReporteServiceInterface {
    List<ReservasPorUsuarioDTO> getReservasPorUsuario();
    List<IngresosPorAlojamientoDTO> getIngresosPorAlojamiento();
    List<PublicacionesPendientesDTO> getPublicacionesPendientes();
    List<UsuariosActivosDTO> getUsuariosActivos();
    List<FavoritosPorUsuarioDTO> getFavoritosPorUsuario();
}
