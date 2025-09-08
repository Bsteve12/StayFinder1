package com.stayFinder.proyectoFinal.services.reservaService.implementations;


import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.stayFinder.proyectoFinal.dto.inputDTO.ActualizarReservaDTO;
import com.stayFinder.proyectoFinal.dto.inputDTO.CreateReservaDTO;
import com.stayFinder.proyectoFinal.dto.outputDTO.ObtenerReservaDTO;
import com.stayFinder.proyectoFinal.entity.Alojamiento;
import com.stayFinder.proyectoFinal.entity.Reserva;
import com.stayFinder.proyectoFinal.entity.Usuario;
import com.stayFinder.proyectoFinal.entity.enums.EstadoReserva;
import com.stayFinder.proyectoFinal.repository.AlojamientoRepository;
import com.stayFinder.proyectoFinal.repository.ReservaRepository;
import com.stayFinder.proyectoFinal.repository.UsuarioRepository;
import com.stayFinder.proyectoFinal.services.reservaService.interfaces.ReservaServiceInterface;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservaServiceImpl implements ReservaServiceInterface {

    private final UsuarioRepository usuarioRepository;
    private final AlojamientoRepository alojamientoRepository;
    private final ReservaRepository reservaRepository;

    @Override
    public void createReserva(CreateReservaDTO createReservaDTO, Long userId) throws Exception {
        Optional<Usuario> user = usuarioRepository.findById(userId);
        if (user.isEmpty()) throw new Exception("Usuario no existe");

        Optional<Alojamiento> alojamiento = alojamientoRepository.findById(createReservaDTO.alojamientoId());
        if (alojamiento.isEmpty()) throw new Exception("Alojamiento no existe");

        Alojamiento alojamientoObject = alojamiento.get();


        Reserva reserva = Reserva.builder()
                .usuario(user.get())
                .alojamiento(alojamientoObject)
                .fecha(createReservaDTO.fecha())
                .precioTotal(alojamientoObject.getPrecio())
                .estado(EstadoReserva.PENDIENTE)
                .build();

        reservaRepository.save(reserva);
    }

    @Override
    public void deleteReserva(Long id) throws Exception {
        Reserva reservaObject = obtenerReservaValida(id);
        reservaObject.setEstado(EstadoReserva.CANCELADA);
        reservaRepository.save(reservaObject);
    }

    @Override
    public void actualizarReserva(ActualizarReservaDTO actualizarReservaDTO) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actualizarReserva'");
    }

    @Override
    public List<ObtenerReservaDTO> obtenerReservasUsuario(Long usuarioId) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'obtenerReservasUsuario'");
    }

    @Override
    public void confirmarReserva(Long id, Long userId) throws Exception {
        Reserva reservaObject = obtenerReservaValida(id);
        if (reservaObject.getUsuario().getId() != userId) throw new Exception("No tienes permisos para confirmar esta reserva");
        reservaObject.setEstado(EstadoReserva.CONFIRMADA);
        reservaRepository.save(reservaObject);

    }

    Reserva  obtenerReservaValida(Long id) throws Exception {
        Optional<Reserva> reserva = reservaRepository.findById(id);
        if (reserva.isEmpty()) throw new Exception("Reserva no existe");
        Reserva reservaObject = reserva.get();
        return reservaObject;
    }

    @Override
    public Optional<Reserva> findById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public Object save(Reserva reserva) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public void deleteById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }

}
