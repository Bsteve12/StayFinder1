package com.stayFinder.proyectoFinal.dto.outputDTO;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MensajeOutputDTO {
    private Long id;
    private Long chatId;
    private Long remitenteId;
    private String contenido;
    private LocalDateTime fechaEnvio;
}
