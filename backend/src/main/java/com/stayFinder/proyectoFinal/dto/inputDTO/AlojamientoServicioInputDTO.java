package com.stayFinder.proyectoFinal.dto.inputDTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlojamientoServicioInputDTO {
    private Long alojamientoId;
    private Long servicioId;
}
