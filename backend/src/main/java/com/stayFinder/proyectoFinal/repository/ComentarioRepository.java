package com.stayFinder.proyectoFinal.repository;



import com.stayFinder.proyectoFinal.entity.Comentario;
import com.stayFinder.proyectoFinal.entity.Alojamiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
    List<Comentario> findByAlojamientoOrderByCreadoEnDesc(Alojamiento alojamiento);
}
