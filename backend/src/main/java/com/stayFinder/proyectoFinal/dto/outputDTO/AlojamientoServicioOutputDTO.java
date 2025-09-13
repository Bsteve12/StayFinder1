package com.stayFinder.proyectoFinal.dto.outputDTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlojamientoServicioOutputDTO {
    private Long alojamientoId;
    private Long servicioId;
    private String servicioNombre;
}
