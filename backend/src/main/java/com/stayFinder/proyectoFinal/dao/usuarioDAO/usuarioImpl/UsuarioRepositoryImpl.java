package com.stayFinder.proyectoFinal.dao.usuarioDAO.usuarioImpl;

import com.stayFinder.proyectoFinal.dao.usuarioDAO.usuarioCustom.UsuarioRepositoryCustom;
import com.stayFinder.proyectoFinal.entity.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UsuarioRepositoryImpl implements UsuarioRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Usuario> buscarUsuariosPorRol(String rol) {
        String jpql = "SELECT u FROM Usuario u WHERE u.rol = :rol";
        return entityManager.createQuery(jpql, Usuario.class)
                .setParameter("rol", rol)
                .getResultList();
    }

    @Override
    public List<Usuario> topUsuariosConMasReservas(int limite) {
        String jpql = "SELECT u FROM Usuario u ORDER BY SIZE(u.reservas) DESC";
        return entityManager.createQuery(jpql, Usuario.class)
                .setMaxResults(limite)
                .getResultList();
    }
}
