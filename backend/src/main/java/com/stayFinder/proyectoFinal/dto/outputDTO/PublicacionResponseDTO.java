package com.stayFinder.proyectoFinal.dto.outputDTO;


import com.stayFinder.proyectoFinal.entity.enums.*;

public class PublicacionResponseDTO {

    private Long id;
    private String titulo;
    private String descripcion;
    private EstadoPublicacion estado;
    private String nombreUsuario;

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public EstadoPublicacion getEstado() { return estado; }
    public void setEstado(EstadoPublicacion estado) { this.estado = estado; }

    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }
}
