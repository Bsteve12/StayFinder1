package com.stayFinder.proyectoFinal.services.reporteService;

import com.stayFinder.proyectoFinal.dto.outputDTO.*;
import com.stayFinder.proyectoFinal.entity.enums.EstadoSolicitudPublicacion;
import com.stayFinder.proyectoFinal.repository.ReportesRepository;
import com.stayFinder.proyectoFinal.services.reporteService.implementations.ReporteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReporteServiceImplTest {

    @Mock
    private ReportesRepository reportesRepository;

    @InjectMocks
    private ReporteServiceImpl reporteService;

    private List<ReservasPorUsuarioDTO> reservasPorUsuario;
    private List<IngresosPorAlojamientoDTO> ingresosPorAlojamiento;
    private List<PublicacionesPendientesDTO> publicacionesPendientes;
    private List<UsuariosActivosDTO> usuariosActivos;
    private List<FavoritosPorUsuarioDTO> favoritosPorUsuario;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Datos simulados
        reservasPorUsuario = List.of(new ReservasPorUsuarioDTO("Allison", 5L));
        ingresosPorAlojamiento = List.of(new IngresosPorAlojamientoDTO("Casa Café", 1500000.0));
        publicacionesPendientes = List.of(new PublicacionesPendientesDTO("Alojamiento 1", EstadoSolicitudPublicacion.PENDIENTE));
        usuariosActivos = List.of(new UsuariosActivosDTO("Allison", "allison@test.com"));
        favoritosPorUsuario = List.of(new FavoritosPorUsuarioDTO("Allison", "Casa Café", 3L));
    }

    @Test
    void getReservasPorUsuario_deberiaRetornarListaCorrecta() {
        when(reportesRepository.obtenerReservasPorUsuario()).thenReturn(reservasPorUsuario);

        List<ReservasPorUsuarioDTO> result = reporteService.getReservasPorUsuario();

        assertEquals(1, result.size());
        assertEquals("Allison", result.get(0).getNombreUsuario());
        assertEquals(5L, result.get(0).getCantidadReservas());
        verify(reportesRepository, times(1)).obtenerReservasPorUsuario();
    }

    @Test
    void getIngresosPorAlojamiento_deberiaRetornarListaCorrecta() {
        when(reportesRepository.obtenerIngresosPorAlojamiento()).thenReturn(ingresosPorAlojamiento);

        List<IngresosPorAlojamientoDTO> result = reporteService.getIngresosPorAlojamiento();

        assertEquals(1, result.size());
        assertEquals("Casa Café", result.get(0).getNombreAlojamiento());
        assertEquals(1500000.0, result.get(0).getIngresosTotales());
        verify(reportesRepository, times(1)).obtenerIngresosPorAlojamiento();
    }

    @Test
    void getPublicacionesPendientes_deberiaRetornarListaCorrecta() {
        when(reportesRepository.obtenerPublicacionesPendientes()).thenReturn(publicacionesPendientes);

        List<PublicacionesPendientesDTO> result = reporteService.getPublicacionesPendientes();

        assertEquals(1, result.size());
        assertEquals("Alojamiento 1", result.get(0).getTitulo());
        assertEquals(EstadoSolicitudPublicacion.PENDIENTE, result.get(0).getEstado());
        verify(reportesRepository, times(1)).obtenerPublicacionesPendientes();
    }

    @Test
    void getUsuariosActivos_deberiaRetornarListaCorrecta() {
        when(reportesRepository.obtenerUsuariosActivos()).thenReturn(usuariosActivos);

        List<UsuariosActivosDTO> result = reporteService.getUsuariosActivos();

        assertEquals(1, result.size());
        assertEquals("Allison", result.get(0).getNombreUsuario());
        assertEquals("allison@test.com", result.get(0).getEmail());
        verify(reportesRepository, times(1)).obtenerUsuariosActivos();
    }

    @Test
    void getFavoritosPorUsuario_deberiaRetornarListaCorrecta() {
        when(reportesRepository.obtenerFavoritosPorUsuario()).thenReturn(favoritosPorUsuario);

        List<FavoritosPorUsuarioDTO> result = reporteService.getFavoritosPorUsuario();

        assertEquals(1, result.size());
        assertEquals("Allison", result.get(0).getNombreUsuario());
        assertEquals(3L, result.get(0).getCantidadFavoritos());
        verify(reportesRepository, times(1)).obtenerFavoritosPorUsuario();
    }
}
