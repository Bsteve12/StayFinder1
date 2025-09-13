package com.stayFinder.proyectoFinal.dto.outputDTO;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagoOutputDTO {
    private Long id;
    private Long reservaId;
    private String metodoPago;
    private Double monto;
    private String estado;
    private LocalDateTime fechaPago;
}
