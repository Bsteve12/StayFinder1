package com.stayFinder.proyectoFinal.repository;



import com.stayFinder.proyectoFinal.entity.Favorite;
import com.stayFinder.proyectoFinal.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findByUsuario(Usuario usuario);
}
