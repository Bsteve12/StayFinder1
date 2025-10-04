package com.stayFinder.proyectoFinal.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "alojamientos")
public class Alojamiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String direccion;
    private Double precio;
    private String descripcion;

    private Integer capacidadMaxima;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private Usuario owner;

    @OneToOne(mappedBy = "alojamiento", cascade = CascadeType.ALL, orphanRemoval = true)
    private Publicacion publicacion;
}
