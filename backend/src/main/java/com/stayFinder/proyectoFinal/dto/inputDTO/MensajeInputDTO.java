package com.stayFinder.proyectoFinal.dto.inputDTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MensajeInputDTO {
    private Long chatId;
    private Long remitenteId;
    private String contenido;
}
