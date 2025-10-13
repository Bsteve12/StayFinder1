package com.stayFinder.proyectoFinal.services.reservaService;

import com.stayFinder.proyectoFinal.dto.inputDTO.ReservaRequestDTO;
import com.stayFinder.proyectoFinal.dto.outputDTO.ReservaResponseDTO;
import com.stayFinder.proyectoFinal.entity.Alojamiento;
import com.stayFinder.proyectoFinal.entity.Reserva;
import com.stayFinder.proyectoFinal.entity.Usuario;
import com.stayFinder.proyectoFinal.entity.enums.EstadoReserva;
import com.stayFinder.proyectoFinal.entity.enums.TipoReserva;
import com.stayFinder.proyectoFinal.mapper.ReservaMapper;
import com.stayFinder.proyectoFinal.repository.AlojamientoRepository;
import com.stayFinder.proyectoFinal.repository.ReservaRepository;
import com.stayFinder.proyectoFinal.repository.UsuarioRepository;
import com.stayFinder.proyectoFinal.services.emailService.interfaces.EmailServiceInterface;
import com.stayFinder.proyectoFinal.services.reservaService.implementations.ReservaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReservaServiceImplTest {

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private AlojamientoRepository alojamientoRepository;

    @Mock
    private ReservaMapper reservaMapper;

    @Mock
    private EmailServiceInterface emailService;

    @InjectMocks
    private ReservaServiceImpl reservaService;

    private Usuario usuario;
    private Alojamiento alojamiento;
    private Reserva reserva;
    private ReservaRequestDTO requestDTO;
    private ReservaResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Allison");
        usuario.setEmail("allison@test.com");

        alojamiento = new Alojamiento();
        alojamiento.setId(2L);
        alojamiento.setNombre("Casa Café");
        alojamiento.setCapacidadMaxima(4);
        alojamiento.setPrecio(100000.0);

        reserva = Reserva.builder()
                .id(10L)
                .usuario(usuario)
                .alojamiento(alojamiento)
                .fechaInicio(LocalDate.now().plusDays(3))
                .fechaFin(LocalDate.now().plusDays(5))
                .numeroHuespedes(2)
                .estado(EstadoReserva.PENDIENTE)
                .tipoReserva(TipoReserva.SENCILLA)
                .precioTotal(190000.0)
                .build();

        requestDTO = new ReservaRequestDTO(
                usuario.getId(),
                alojamiento.getId(),
                reserva.getFechaInicio(),
                reserva.getFechaFin(),
                reserva.getNumeroHuespedes(),
                reserva.getTipoReserva()
        );

        responseDTO = new ReservaResponseDTO(
                reserva.getId(),
                usuario.getId(),
                alojamiento.getId(),
                reserva.getFechaInicio(),
                reserva.getFechaFin(),
                reserva.getNumeroHuespedes(),
                reserva.getPrecioTotal(),
                reserva.getEstado()
        );
    }

    @Test
    void createReserva_deberiaCrearReservaConExito() throws Exception {
        when(usuarioRepository.findById(usuario.getId())).thenReturn(Optional.of(usuario));
        when(alojamientoRepository.findById(alojamiento.getId())).thenReturn(Optional.of(alojamiento));
        when(reservaMapper.toEntity(requestDTO)).thenReturn(reserva);
        when(reservaRepository.save(any(Reserva.class))).thenReturn(reserva);
        when(reservaMapper.toDto(reserva)).thenReturn(responseDTO);

        ReservaResponseDTO result = reservaService.createReserva(requestDTO, usuario.getId());

        assertNotNull(result);
        assertEquals(usuario.getId(), result.usuarioId());
        assertEquals(alojamiento.getId(), result.alojamientoId());
        verify(emailService, times(1)).sendReservationConfirmation(
                eq(usuario.getEmail()), anyString(), anyString()
        );
    }

    @Test
    void createReserva_deberiaLanzarExcepcionSiUsuarioNoExiste() {
        when(usuarioRepository.findById(usuario.getId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () ->
                reservaService.createReserva(requestDTO, usuario.getId())
        );

        assertEquals("Usuario no existe", exception.getMessage());
    }

    @Test
    void confirmarReserva_deberiaCambiarEstadoYEnviarCorreo() throws Exception {
        reserva.setEstado(EstadoReserva.PENDIENTE);
        when(reservaRepository.findById(reserva.getId())).thenReturn(Optional.of(reserva));
        when(reservaRepository.save(reserva)).thenReturn(reserva);

        reservaService.confirmarReserva(reserva.getId(), usuario.getId());

        assertEquals(EstadoReserva.CONFIRMADA, reserva.getEstado());
        verify(emailService, times(1)).sendReservationConfirmation(
                eq(usuario.getEmail()), anyString(), anyString()
        );
    }

    @Test
    void deleteReserva_deberiaEliminarReservaExistente() throws Exception {
        when(reservaRepository.findById(reserva.getId())).thenReturn(Optional.of(reserva));

        reservaService.deleteReserva(reserva.getId());

        verify(reservaRepository, times(1)).delete(reserva);
    }

    @Test
    void obtenerReservasUsuario_deberiaRetornarListaDeReservas() throws Exception {
        when(usuarioRepository.findById(usuario.getId())).thenReturn(Optional.of(usuario));
        when(reservaRepository.findByUsuarioId(usuario.getId())).thenReturn(List.of(reserva));
        when(reservaMapper.toDto(reserva)).thenReturn(responseDTO);

        List<ReservaResponseDTO> result = reservaService.obtenerReservasUsuario(usuario.getId());

        assertEquals(1, result.size());
        assertEquals(reserva.getId(), result.get(0).id());
    }

    @Test
    void findById_deberiaRetornarReservaSiExiste() {
        when(reservaRepository.findById(reserva.getId())).thenReturn(Optional.of(reserva));
        when(reservaMapper.toDto(reserva)).thenReturn(responseDTO);

        Optional<ReservaResponseDTO> result = reservaService.findById(reserva.getId());

        assertTrue(result.isPresent());
        assertEquals(reserva.getId(), result.get().id());
    }

    @Test
    void findById_deberiaRetornarVacioSiNoExiste() {
        when(reservaRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<ReservaResponseDTO> result = reservaService.findById(99L);

        assertTrue(result.isEmpty());
    }
}
