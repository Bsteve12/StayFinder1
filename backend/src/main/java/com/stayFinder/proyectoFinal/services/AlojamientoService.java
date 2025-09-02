package com.stayFinder.proyectoFinal.services;

import com.stayFinder.proyectoFinal.entity.Alojamiento;
import com.stayFinder.proyectoFinal.repository.AlojamientoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlojamientoService {

    private final AlojamientoRepository repo;

    public AlojamientoService(AlojamientoRepository repo) {
        this.repo = repo;
    }

    public List<Alojamiento> findAll() {
        return repo.findAll();
    }

    public Alojamiento save(Alojamiento alojamiento) {
        return repo.save(alojamiento);
    }

    public Optional<Alojamiento> findById(Long id) {
        return repo.findById(id);
    }

    public void deleteById(Long id) {
        repo.deleteById(id);
    }
}
