package com.stayFinder.proyectoFinal.services;

import com.stayFinder.proyectoFinal.entity.Reserva;
import com.stayFinder.proyectoFinal.repository.ReservaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservaService {

    private final ReservaRepository repo;

    public ReservaService(ReservaRepository repo) { 
        this.repo = repo; 
    }

    public List<Reserva> findAll() { 
        return repo.findAll(); 
    }

    public Reserva save(Reserva reserva) { 
        return repo.save(reserva); 
    }

    public Optional<Reserva> findById(Long id) {
        return repo.findById(id);  // ✅ Aquí también había el mismo error
    }

    public void deleteById(Long id) {
        repo.deleteById(id);  // ✅ Igual aquí
    }

}
