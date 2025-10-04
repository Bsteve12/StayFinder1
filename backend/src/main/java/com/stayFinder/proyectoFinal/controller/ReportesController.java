package com.stayFinder.proyectoFinal.controller;

import com.stayFinder.proyectoFinal.dto.outputDTO.*;
import com.stayFinder.proyectoFinal.services.reporteService.interfaces.ReporteServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reportes")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ReportesController {

    private final ReporteServiceInterface reporteService;

    @GetMapping("/reservas-por-usuario")
    public List<ReservasPorUsuarioDTO> reservasPorUsuario() {
        return reporteService.getReservasPorUsuario();
    }

    @GetMapping("/ingresos-por-alojamiento")
    public List<IngresosPorAlojamientoDTO> ingresosPorAlojamiento() {
        return reporteService.getIngresosPorAlojamiento();
    }

    @GetMapping("/publicaciones-pendientes")
    public List<PublicacionesPendientesDTO> publicacionesPendientes() {
        return reporteService.getPublicacionesPendientes();
    }

    @GetMapping("/usuarios-activos")
    public List<UsuariosActivosDTO> usuariosActivos() {
        return reporteService.getUsuariosActivos();
    }

    @GetMapping("/favoritos-por-usuario")
    public List<FavoritosPorUsuarioDTO> favoritosPorUsuario() {
        return reporteService.getFavoritosPorUsuario();
    }
}
