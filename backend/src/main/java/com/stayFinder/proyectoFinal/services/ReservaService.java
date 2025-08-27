package com.stayFinder.proyectoFinal.services;

import com.stayFinder.proyectoFinal.entity.Producto;
import com.stayFinder.proyectoFinal.repository.ProductoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservaService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> findAll() {
        return productoRepository.findAll();
    }

    public Optional<Producto> findById(Long id) {
        if (id == null || id <= 0) {
            return Optional.empty();
        }
        return productoRepository.findById(id);
    }

    public Producto save(@Valid Producto producto) {
        if (producto == null ||
                producto.getNombre() == null || producto.getNombre().isBlank() ||
                producto.getPrecio() == null || producto.getPrecio() < 0) {
            throw new IllegalArgumentException("Datos del producto no válidos");
        }
        return productoRepository.save(producto);
    }

    public void deleteById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID no válido");
        }
        productoRepository.deleteById(id);
    }
}
