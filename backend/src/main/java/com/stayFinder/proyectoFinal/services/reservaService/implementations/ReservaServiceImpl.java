package com.stayFinder.proyectoFinal.services.reservaService.implementations;

import com.stayFinder.proyectoFinal.dto.inputDTO.ActualizarReservaDTO;
import com.stayFinder.proyectoFinal.dto.inputDTO.CancelarReservaDTO;
import com.stayFinder.proyectoFinal.dto.inputDTO.ReservaRequestDTO;
import com.stayFinder.proyectoFinal.dto.outputDTO.ReservaResponseDTO;
import com.stayFinder.proyectoFinal.entity.Alojamiento;
import com.stayFinder.proyectoFinal.entity.Reserva;
import com.stayFinder.proyectoFinal.entity.Usuario;
import com.stayFinder.proyectoFinal.entity.enums.EstadoReserva;
import com.stayFinder.proyectoFinal.mapper.ReservaMapper;
import com.stayFinder.proyectoFinal.repository.AlojamientoRepository;
import com.stayFinder.proyectoFinal.repository.ReservaRepository;
import com.stayFinder.proyectoFinal.repository.UsuarioRepository;
import com.stayFinder.proyectoFinal.services.reservaService.interfaces.ReservaServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservaServiceImpl implements ReservaServiceInterface {

    private final UsuarioRepository usuarioRepository;
    private final AlojamientoRepository alojamientoRepository;
    private final ReservaRepository reservaRepository;
    private final ReservaMapper reservaMapper;

    // =============================
    // CREAR RESERVA
    // =============================
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

        long noches = ChronoUnit.DAYS.between(dto.fechaInicio(), dto.fechaFin());
        reserva.setPrecioTotal(noches * alojamiento.getPrecio());

        Reserva saved = reservaRepository.save(reserva);
        return reservaMapper.toDto(saved);
    }
    @Override
    public void cancelarReserva(CancelarReservaDTO dto, Long userId) throws Exception {
        Reserva reserva = obtenerReservaValida(dto.reservaId());

        // Validar que la reserva pertenezca al usuario
        if (!reserva.getUsuario().getId().equals(userId)) {
            throw new Exception("No tienes permisos para cancelar esta reserva");
        }

        // Validar que la reserva no sea pasada
        if (reserva.getFechaInicio().isBefore(LocalDate.now())) {
            throw new Exception("No se puede cancelar una reserva que ya inició o terminó");
        }

        reserva.setEstado(EstadoReserva.CANCELADA);

        // (Opcional) Guardar motivo de cancelación en un campo si lo agregas en la entidad
        // Por ahora, solo mostramos en consola
        if (dto.motivo() != null && !dto.motivo().isBlank()) {
            System.out.println("Reserva cancelada con motivo: " + dto.motivo());
        }

        reservaRepository.save(reserva);
    }
    // =============================
    // ELIMINAR RESERVA (cancelar)
    // =============================
    @Override
    public void deleteReserva(Long id) throws Exception {
        Reserva reserva = obtenerReservaValida(id);

        if (reserva.getFechaInicio().isBefore(LocalDate.now())) {
            throw new Exception("No se puede cancelar una reserva pasada");
        }

        reserva.setEstado(EstadoReserva.CANCELADA);
        reservaRepository.save(reserva);
    }

    // =============================
    // ACTUALIZAR RESERVA
    // =============================
    @Override
    public ReservaResponseDTO actualizarReserva(ActualizarReservaDTO dto, Long userId) throws Exception {
        Reserva reserva = obtenerReservaValida(dto.reservaId());

        if (!reserva.getUsuario().getId().equals(userId)) {
            throw new Exception("No tienes permisos para actualizar esta reserva");
        }

        validarFechas(dto.fechaInicio(), dto.fechaFin());
        validarCapacidad(dto.numeroHuespedes(), reserva.getAlojamiento().getCapacidadMaxima());
        validarDisponibilidad(dto.fechaInicio(), dto.fechaFin(), reserva.getAlojamiento().getId());

        reserva.setFechaInicio(dto.fechaInicio());
        reserva.setFechaFin(dto.fechaFin());
        reserva.setNumeroHuespedes(dto.numeroHuespedes());

        long noches = ChronoUnit.DAYS.between(dto.fechaInicio(), dto.fechaFin());
        reserva.setPrecioTotal(noches * reserva.getAlojamiento().getPrecio());

        Reserva saved = reservaRepository.save(reserva);
        return reservaMapper.toDto(saved);
    }

    // =============================
    // LISTAR RESERVAS DE UN USUARIO
    // =============================
    @Override
    public List<ReservaResponseDTO> obtenerReservasUsuario(Long usuarioId) throws Exception {
        usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new Exception("Usuario no existe"));

        return reservaRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(reservaMapper::toDto)
                .toList();
    }

    // =============================
    // CONFIRMAR RESERVA
    // =============================
    @Override
    public void confirmarReserva(Long id, Long userId) throws Exception {
        Reserva reserva = obtenerReservaValida(id);

        if (!reserva.getUsuario().getId().equals(userId)) {
            throw new Exception("No puedes confirmar una reserva que no es tuya");
        }

        reserva.setEstado(EstadoReserva.CONFIRMADA);
        reservaRepository.save(reserva);
    }

    // =============================
    // OBTENER POR ID
    // =============================
    @Override
    public Optional<ReservaResponseDTO> findById(Long id) {
        return reservaRepository.findById(id).map(reservaMapper::toDto);
    }

    // =============================
    // GUARDAR RESERVA (update general)
    // =============================
    @Override
    public ReservaResponseDTO save(ReservaRequestDTO dto) throws Exception {
        Reserva reserva = reservaMapper.toEntity(dto);

        Usuario usuario = usuarioRepository.findById(dto.usuarioId())
                .orElseThrow(() -> new Exception("Usuario no existe"));

        Alojamiento alojamiento = alojamientoRepository.findById(dto.alojamientoId())
                .orElseThrow(() -> new Exception("Alojamiento no existe"));

        validarFechas(dto.fechaInicio(), dto.fechaFin());
        validarCapacidad(dto.numeroHuespedes(), alojamiento.getCapacidadMaxima());
        validarDisponibilidad(dto.fechaInicio(), dto.fechaFin(), alojamiento.getId());

        reserva.setUsuario(usuario);
        reserva.setAlojamiento(alojamiento);

        long noches = ChronoUnit.DAYS.between(dto.fechaInicio(), dto.fechaFin());
        reserva.setPrecioTotal(noches * alojamiento.getPrecio());

        Reserva saved = reservaRepository.save(reserva);
        return reservaMapper.toDto(saved);
    }

    // =============================
    // ELIMINAR POR ID
    // =============================
    @Override
    public void deleteById(Long id, Long userId) throws Exception {
        Reserva reserva = obtenerReservaValida(id);

        if (!reserva.getUsuario().getId().equals(userId)) {
            throw new Exception("No tienes permisos para eliminar esta reserva");
        }

        reservaRepository.delete(reserva);
    }

    // =============================
    // MÉTODOS DE VALIDACIÓN
    // =============================
    private Reserva obtenerReservaValida(Long id) throws Exception {
        return reservaRepository.findById(id)
                .orElseThrow(() -> new Exception("Reserva no existe"));
    }

    private void validarFechas(LocalDate inicio, LocalDate fin) throws Exception {
        if (inicio.isBefore(LocalDate.now())) {
            throw new Exception("La fecha de inicio no puede ser en el pasado");
        }
        if (!fin.isAfter(inicio)) {
            throw new Exception("La fecha de fin debe ser después de la fecha de inicio");
        }
        if (ChronoUnit.DAYS.between(inicio, fin) < 1) {
            throw new Exception("La reserva debe ser al menos de 1 noche");
        }
    }

    private void validarCapacidad(int huespedes, int capacidadMaxima) throws Exception {
        if (huespedes > capacidadMaxima) {
            throw new Exception("El número de huéspedes supera la capacidad del alojamiento");
        }
    }

    private void validarDisponibilidad(LocalDate inicio, LocalDate fin, Long alojamientoId) throws Exception {
        List<Reserva> reservas = reservaRepository.findByAlojamientoIdAndEstado(alojamientoId, EstadoReserva.CONFIRMADA);

        boolean solapado = reservas.stream().anyMatch(r ->
                (inicio.isBefore(r.getFechaFin()) && fin.isAfter(r.getFechaInicio()))
        );

        if (solapado) {
            throw new Exception("El alojamiento no está disponible en esas fechas");
        }
    }
}
