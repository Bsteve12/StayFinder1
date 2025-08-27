package com.stayFinder.proyectoFinal.services;

import com.stayFinder.proyectoFinal.entity.Reserva;
import com.stayFinder.proyectoFinal.repository.ReservaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    public List<Reserva> findAll() {
        return reservaRepository.findAll();
    }

    public Optional<Reserva> findById(Long id) {
        if (id == null || id <= 0) {
            return Optional.empty();
        }
        return reservaRepository.findById(id);
    }

    public Reserva save(@Valid Reserva reserva) {
        if (reserva == null ||
                reserva.getNombre() == null || reserva.getNombre().isBlank() ||
                reserva.getPrecio() == null || reserva.getPrecio() < 0) {
            throw new IllegalArgumentException("Datos del producto no válidos");
        }
        return reservaRepository.save(reserva);
    }

    public void deleteById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID no válido");
        }
        reservaRepository.deleteById(id);
    }
}
