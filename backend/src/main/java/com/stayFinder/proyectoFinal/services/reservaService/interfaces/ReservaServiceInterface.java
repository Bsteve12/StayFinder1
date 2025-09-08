package com.stayFinder.proyectoFinal.services.reservaService.interfaces;


import java.util.List;
import java.util.Optional;

import com.stayFinder.proyectoFinal.dto.inputDTO.ActualizarReservaDTO;
import com.stayFinder.proyectoFinal.dto.inputDTO.CreateReservaDTO;
import com.stayFinder.proyectoFinal.dto.outputDTO.ObtenerReservaDTO;
import com.stayFinder.proyectoFinal.entity.Reserva;

public interface ReservaServiceInterface {

    void createReserva(CreateReservaDTO createReservaDTO, Long userId) throws Exception;

    void deleteReserva(Long id) throws Exception;

    void actualizarReserva(ActualizarReservaDTO actualizarReservaDTO) throws Exception;

    List<ObtenerReservaDTO> obtenerReservasUsuario(Long usuarioId) throws Exception;

    void confirmarReserva(Long id, Long userId) throws Exception;

    Optional<Reserva> findById(Long id);

    Object save(Reserva reserva);

    void deleteById(Long id);

}
