package com.stayFinder.proyectoFinal.dto.inputDTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagoInputDTO {
    private Long reservaId;
    private String metodoPago;
    private Double monto;
}
