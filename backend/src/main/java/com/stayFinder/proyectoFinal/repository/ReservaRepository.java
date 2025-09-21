package com.stayFinder.proyectoFinal.repository;

import com.stayFinder.proyectoFinal.dao.reservaDAO.reservaCustom.ReservaRepositoryCustom;
import com.stayFinder.proyectoFinal.entity.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> , ReservaRepositoryCustom {
    List<Reserva> findByUsuarioId(Long usuarioId);
    List<Reserva> findByAlojamientoId(Long alojamientoId);
}
