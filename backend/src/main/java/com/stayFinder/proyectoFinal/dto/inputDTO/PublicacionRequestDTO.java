package com.stayFinder.proyectoFinal.dto.inputDTO;


public class PublicacionRequestDTO {

    private String titulo;
    private String descripcion;
    private Long usuarioId; // ID del usuario que crea la publicación

    // Getters y setters
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
}