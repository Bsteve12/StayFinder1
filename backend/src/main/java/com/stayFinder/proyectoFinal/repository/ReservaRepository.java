package com.stayFinder.proyectoFinal.repository;

import com.stayFinder.proyectoFinal.entity.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
}
