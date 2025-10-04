package com.stayFinder.proyectoFinal.services.reporteService.implementations;

import com.stayFinder.proyectoFinal.dto.outputDTO.*;
import com.stayFinder.proyectoFinal.repository.ReportesRepository;
import com.stayFinder.proyectoFinal.services.reporteService.interfaces.ReporteServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReporteServiceImpl implements ReporteServiceInterface {

    private final ReportesRepository reportesRepository;

    @Override
    public List<ReservasPorUsuarioDTO> getReservasPorUsuario() {
        return reportesRepository.obtenerReservasPorUsuario();
    }

    @Override
    public List<IngresosPorAlojamientoDTO> getIngresosPorAlojamiento() {
        return reportesRepository.obtenerIngresosPorAlojamiento();
    }

    @Override
    public List<PublicacionesPendientesDTO> getPublicacionesPendientes() {
        return reportesRepository.obtenerPublicacionesPendientes();
    }

    @Override
    public List<UsuariosActivosDTO> getUsuariosActivos() {
        return reportesRepository.obtenerUsuariosActivos();
    }

    @Override
    public List<FavoritosPorUsuarioDTO> getFavoritosPorUsuario() {
        return reportesRepository.obtenerFavoritosPorUsuario();
    }
}
