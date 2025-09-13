package com.stayFinder.proyectoFinal.dto.outputDTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServicioOutputDTO {
    private Long id;
    private String nombre;
    private String descripcion;
}
