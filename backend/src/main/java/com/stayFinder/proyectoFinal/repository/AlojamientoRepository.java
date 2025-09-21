package com.stayFinder.proyectoFinal.repository;

import com.stayFinder.proyectoFinal.dao.alojamientoDAO.alojamientoCustom.AlojamientoRepositoryCustom;
import com.stayFinder.proyectoFinal.entity.Alojamiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlojamientoRepository extends JpaRepository<Alojamiento, Long>, AlojamientoRepositoryCustom {
    // Puedes agregar consultas personalizadas aquí si las necesitas
}
