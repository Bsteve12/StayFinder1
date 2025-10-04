package com.stayFinder.proyectoFinal.dto.outputDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import com.stayFinder.proyectoFinal.entity.enums.EstadoSolicitudPublicacion;

@Schema(description = "Respuesta con la información de una publicación")
public class PublicacionResponseDTO {

    @Schema(description = "ID de la publicación", example = "120")
    private Long id;

    @Schema(description = "Título de la publicación", example = "Hermosa finca en el Quindío")
    private String titulo;

    @Schema(description = "Descripción de la publicación", example = "Alojamiento con piscina y zona de BBQ")
    private String descripcion;

    @Schema(description = "Estado de la publicación", example = "APROBADA")
    private EstadoSolicitudPublicacion estado;

    @Schema(description = "Nombre del usuario que publicó", example = "Carlos Pérez")
    private String nombreUsuario;

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public EstadoSolicitudPublicacion getEstado() { return estado; }
    public void setEstado(EstadoSolicitudPublicacion estado) { this.estado = estado; }

    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }
}
