package com.stayFinder.proyectoFinal.repository;

import com.stayFinder.proyectoFinal.entity.SolicitudOwner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SolicitudOwnerRepository extends JpaRepository<SolicitudOwner, Long> {
    List<SolicitudOwner> findByEstado(String estado);
}
