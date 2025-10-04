package com.stayFinder.proyectoFinal.repository;

import com.stayFinder.proyectoFinal.dao.reservaDAO.reservaCustom.ReservaRepositoryCustom;
import com.stayFinder.proyectoFinal.entity.Reserva;
import com.stayFinder.proyectoFinal.entity.Usuario;
import com.stayFinder.proyectoFinal.entity.enums.EstadoReserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    List<Reserva> findByUsuarioId(Long usuarioId);

    List<Reserva> findByAlojamientoIdAndEstado(Long alojamientoId, EstadoReserva estado);
    boolean existsByUsuarioAndAlojamientoOwnerAndEstado(Usuario usuario, Usuario owner, EstadoReserva estado);
}

