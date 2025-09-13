package com.stayFinder.proyectoFinal.dto.inputDTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatInputDTO {
    private Long usuarioId;
    private Long anfitrionId;
}
