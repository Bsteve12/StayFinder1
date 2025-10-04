package com.stayFinder.proyectoFinal.services.reservaService.interfaces;

import com.stayFinder.proyectoFinal.dto.inputDTO.ActualizarReservaDTO;
import com.stayFinder.proyectoFinal.dto.inputDTO.CancelarReservaDTO;
import com.stayFinder.proyectoFinal.dto.inputDTO.ReservaRequestDTO;
import com.stayFinder.proyectoFinal.dto.outputDTO.ReservaResponseDTO;
import com.stayFinder.proyectoFinal.dto.inputDTO.HistorialReservasRequestDTO;
import com.stayFinder.proyectoFinal.dto.outputDTO.ReservaHistorialDTO;

import java.util.List;
import java.util.Optional;

public interface ReservaServiceInterface {

    // Crear una nueva reserva
    ReservaResponseDTO createReserva(ReservaRequestDTO dto, Long userId) throws Exception;


    // Cancelar reserva (con motivo opcional)
    void cancelarReserva(CancelarReservaDTO dto, Long userId) throws Exception;

    // =============================
    // ELIMINAR RESERVA (cancelar)
    // =============================
    void deleteReserva(Long id) throws Exception;

    // Actualizar datos de una reserva
    ReservaResponseDTO actualizarReserva(ActualizarReservaDTO dto, Long userId) throws Exception;

    // Confirmar reserva
    void confirmarReserva(Long id, Long userId) throws Exception;

    // Obtener reservas por usuario
    List<ReservaResponseDTO> obtenerReservasUsuario(Long usuarioId) throws Exception;

    // Métodos utilitarios
    Optional<ReservaResponseDTO> findById(Long id);
    ReservaResponseDTO save(ReservaRequestDTO dto) throws Exception;
    void deleteById(Long id, Long userId) throws Exception;
    List<ReservaHistorialDTO> historialReservasUsuario(Long usuarioId, HistorialReservasRequestDTO filtros) throws Exception;

    List<ReservaHistorialDTO> historialReservasAnfitrion(Long ownerId, HistorialReservasRequestDTO filtros) throws Exception;
}

