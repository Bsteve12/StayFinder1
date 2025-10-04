package com.stayFinder.proyectoFinal.repository;

import com.stayFinder.proyectoFinal.dao.alojamientoDAO.alojamientoCustom.AlojamientoRepositoryCustom;
import com.stayFinder.proyectoFinal.entity.Alojamiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlojamientoRepository extends JpaRepository<Alojamiento, Long>, AlojamientoRepositoryCustom {
    List<Alojamiento> findByOwnerId(Long ownerId);
    // Puedes agregar consultas personalizadas aquí si las necesitas
}
