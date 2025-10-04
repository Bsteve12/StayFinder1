package com.stayFinder.proyectoFinal.entity;

import com.stayFinder.proyectoFinal.entity.enums.EstadoSolicitudPublicacion;
import jakarta.persistence.*;

@Entity
@Table(name = "publicaciones")
public class Publicacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String descripcion;

    @Enumerated(EnumType.STRING)
    private EstadoSolicitudPublicacion estado;

    // Relación con Usuario (quien crea la publicación)
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    // Relación con Alojamiento
    @OneToOne
    @JoinColumn(name = "alojamiento_id", referencedColumnName = "id")
    private Alojamiento alojamiento;

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public EstadoSolicitudPublicacion getEstado() { return estado; }
    public void setEstado(EstadoSolicitudPublicacion estado) { this.estado = estado; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public Alojamiento getAlojamiento() { return alojamiento; }
    public void setAlojamiento(Alojamiento alojamiento) { this.alojamiento = alojamiento; }
}
