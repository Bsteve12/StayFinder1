package com.stayFinder.proyectoFinal.services.reservaService.implementations;

import com.stayFinder.proyectoFinal.dto.inputDTO.ActualizarReservaDTO;
import com.stayFinder.proyectoFinal.dto.inputDTO.CancelarReservaDTO;
import com.stayFinder.proyectoFinal.dto.inputDTO.ReservaRequestDTO;
import com.stayFinder.proyectoFinal.dto.inputDTO.HistorialReservasRequestDTO;
import com.stayFinder.proyectoFinal.dto.outputDTO.ReservaHistorialDTO;
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
import com.stayFinder.proyectoFinal.services.reservaService.interfaces.ReservaServiceInterface;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class ReservaServiceImpl implements ReservaServiceInterface {

    private final ReservaRepository reservaRepository;
    private final UsuarioRepository usuarioRepository;
    private final AlojamientoRepository alojamientoRepository;
    private final ReservaMapper reservaMapper;

    public ReservaServiceImpl(ReservaRepository reservaRepository,
                              UsuarioRepository usuarioRepository,
                              AlojamientoRepository alojamientoRepository,
                              ReservaMapper reservaMapper) {
        this.reservaRepository = reservaRepository;
        this.usuarioRepository = usuarioRepository;
        this.alojamientoRepository = alojamientoRepository;
        this.reservaMapper = reservaMapper;
    }

    // ------------------------- MÉTODOS EXISTENTES -------------------------

    @Override
    public ReservaResponseDTO createReserva(ReservaRequestDTO dto, Long userId) throws Exception {
        Usuario usuario = usuarioRepository.findById(userId)
                .orElseThrow(() -> new Exception("Usuario no existe"));

        Alojamiento alojamiento = alojamientoRepository.findById(dto.alojamientoId())
                .orElseThrow(() -> new Exception("Alojamiento no existe"));

        validarFechas(dto.fechaInicio(), dto.fechaFin());
        validarCapacidad(dto.numeroHuespedes(), alojamiento.getCapacidadMaxima());
        validarDisponibilidad(dto.fechaInicio(), dto.fechaFin(), alojamiento.getId());

        Reserva reserva = reservaMapper.toEntity(dto);
        reserva.setUsuario(usuario);
        reserva.setAlojamiento(alojamiento);
        reserva.setEstado(EstadoReserva.PENDIENTE);
        reserva.setPrecioTotal(calcularPrecioTotal(dto, alojamiento));

        Reserva saved = reservaRepository.save(reserva);
        return reservaMapper.toDto(saved);
    }

    @Override
    public void cancelarReserva(CancelarReservaDTO dto, Long userId) throws Exception {
        Reserva reserva = obtenerReservaValida(dto.reservaId(), userId);
        reserva.setEstado(EstadoReserva.CANCELADA);
        reservaRepository.save(reserva);
    }

    @Override
    public void deleteReserva(Long id) throws Exception {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new Exception("Reserva no encontrada"));
        reservaRepository.delete(reserva);
    }

    @Override
    public ReservaResponseDTO actualizarReserva(ActualizarReservaDTO dto, Long userId) throws Exception {
        Reserva reserva = obtenerReservaValida(dto.reservaId(), userId);

        validarFechas(dto.fechaInicio(), dto.fechaFin());
        validarCapacidad(dto.numeroHuespedes(), reserva.getAlojamiento().getCapacidadMaxima());
        validarDisponibilidad(dto.fechaInicio(), dto.fechaFin(), reserva.getAlojamiento().getId());

        reserva.setFechaInicio(dto.fechaInicio());
        reserva.setFechaFin(dto.fechaFin());
        reserva.setNumeroHuespedes(dto.numeroHuespedes());
        reserva.setTipoReserva(dto.tipoReserva());

        ReservaRequestDTO tempDto = new ReservaRequestDTO(
                reserva.getUsuario().getId(),
                reserva.getAlojamiento().getId(),
                reserva.getFechaInicio(),
                reserva.getFechaFin(),
                reserva.getNumeroHuespedes(),
                reserva.getTipoReserva()
        );
        reserva.setPrecioTotal(calcularPrecioTotal(tempDto, reserva.getAlojamiento()));

        Reserva saved = reservaRepository.save(reserva);
        return reservaMapper.toDto(saved);
    }

    @Override
    public void confirmarReserva(Long id, Long userId) throws Exception {
        Reserva reserva = obtenerReservaValida(id, userId);
        reserva.setEstado(EstadoReserva.CONFIRMADA);
        reservaRepository.save(reserva);
    }

    @Override
    public List<ReservaResponseDTO> obtenerReservasUsuario(Long usuarioId) throws Exception {
        usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new Exception("Usuario no existe"));

        return reservaRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(reservaMapper::toDto)
                .toList();
    }

    @Override
    public Optional<ReservaResponseDTO> findById(Long id) {
        return reservaRepository.findById(id).map(reservaMapper::toDto);
    }

    @Override
    public ReservaResponseDTO save(ReservaRequestDTO dto) throws Exception {
        Alojamiento alojamiento = alojamientoRepository.findById(dto.alojamientoId())
                .orElseThrow(() -> new Exception("Alojamiento no existe"));
        Usuario usuario = usuarioRepository.findById(dto.usuarioId())
                .orElseThrow(() -> new Exception("Usuario no existe"));

        Reserva reserva = reservaMapper.toEntity(dto);
        reserva.setUsuario(usuario);
        reserva.setAlojamiento(alojamiento);
        reserva.setEstado(EstadoReserva.PENDIENTE);
        reserva.setPrecioTotal(calcularPrecioTotal(dto, alojamiento));

        Reserva saved = reservaRepository.save(reserva);
        return reservaMapper.toDto(saved);
    }

    @Override
    public void deleteById(Long id, Long userId) throws Exception {
        Reserva reserva = obtenerReservaValida(id, userId);
        reservaRepository.delete(reserva);
    }

    // ------------------------- NUEVOS MÉTODOS: HISTORIAL -------------------------

    @Override
    public List<ReservaHistorialDTO> historialReservasUsuario(Long usuarioId, HistorialReservasRequestDTO filtros) throws Exception {
        usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new Exception("Usuario no existe"));

        List<Reserva> reservas = reservaRepository.findByUsuarioId(usuarioId);

        return reservas.stream()
                .filter(r -> filtros.fechaInicio() == null || !r.getFechaInicio().isBefore(filtros.fechaInicio()))
                .filter(r -> filtros.fechaFin() == null || !r.getFechaFin().isAfter(filtros.fechaFin()))
                .filter(r -> filtros.estado() == null || r.getEstado() == filtros.estado())
                .map(r -> new ReservaHistorialDTO(
                        r.getId(),
                        r.getAlojamiento().getId(),
                        r.getAlojamiento().getNombre(),
                        r.getUsuario().getId(),
                        r.getUsuario().getNombre(),
                        r.getFechaInicio(),
                        r.getFechaFin(),
                        r.getNumeroHuespedes(),
                        r.getPrecioTotal(),
                        r.getEstado(),
                        r.getTipoReserva()
                ))
                .toList();
    }

    @Override
    public List<ReservaHistorialDTO> historialReservasAnfitrion(Long ownerId, HistorialReservasRequestDTO filtros) throws Exception {
        usuarioRepository.findById(ownerId)
                .orElseThrow(() -> new Exception("Usuario no existe"));

        List<Alojamiento> alojamientos = alojamientoRepository.findByOwnerId(ownerId);

        return alojamientos.stream()
                .flatMap(a -> reservaRepository.findByAlojamientoId(a.getId()).stream())
                .filter(r -> filtros.fechaInicio() == null || !r.getFechaInicio().isBefore(filtros.fechaInicio()))
                .filter(r -> filtros.fechaFin() == null || !r.getFechaFin().isAfter(filtros.fechaFin()))
                .filter(r -> filtros.estado() == null || r.getEstado() == filtros.estado())
                .map(r -> new ReservaHistorialDTO(
                        r.getId(),
                        r.getAlojamiento().getId(),
                        r.getAlojamiento().getNombre(),
                        r.getUsuario().getId(),
                        r.getUsuario().getNombre(),
                        r.getFechaInicio(),
                        r.getFechaFin(),
                        r.getNumeroHuespedes(),
                        r.getPrecioTotal(),
                        r.getEstado(),
                        r.getTipoReserva()
                ))
                .toList();
    }

    // ------------------------- MÉTODOS AUXILIARES -------------------------

    private void validarFechas(LocalDate inicio, LocalDate fin) throws Exception {
        if (inicio.isAfter(fin)) {
            throw new Exception("La fecha de inicio no puede ser posterior a la fecha de fin");
        }
        if (inicio.isBefore(LocalDate.now())) {
            throw new Exception("La fecha de inicio no puede ser en el pasado");
        }
    }

    private void validarCapacidad(int numeroHuespedes, int capacidadMaxima) throws Exception {
        if (numeroHuespedes > capacidadMaxima) {
            throw new Exception("El número de huéspedes excede la capacidad máxima");
        }
    }

    private void validarDisponibilidad(LocalDate inicio, LocalDate fin, Long alojamientoId) throws Exception {
        List<Reserva> reservas = reservaRepository.findByAlojamientoIdAndEstado(alojamientoId, EstadoReserva.CONFIRMADA);
        for (Reserva r : reservas) {
            if (!(fin.isBefore(r.getFechaInicio()) || inicio.isAfter(r.getFechaFin()))) {
                throw new Exception("El alojamiento no está disponible en las fechas seleccionadas");
            }
        }
    }

    private Reserva obtenerReservaValida(Long reservaId, Long userId) throws Exception {
        Reserva reserva = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new Exception("Reserva no encontrada"));
        if (!reserva.getUsuario().getId().equals(userId)) {
            throw new Exception("No tiene permiso para modificar esta reserva");
        }
        return reserva;
    }

    private double calcularPrecioTotal(ReservaRequestDTO dto, Alojamiento alojamiento) {
        long noches = ChronoUnit.DAYS.between(dto.fechaInicio(), dto.fechaFin());
        if (noches <= 0) {
            noches = 1;
        }

        double precioBase = noches * alojamiento.getPrecio();

        if (dto.tipoReserva() == TipoReserva.VIP) {
            if (noches > 7) {
                precioBase *= 0.5;
            }
            precioBase *= 0.9;
        } else {
            precioBase *= 0.95;
        }

        return precioBase;
    }
}
