package com.stayFinder.proyectoFinal.dto.outputDTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatOutputDTO {
    private Long id;
    private Long usuarioId;
    private Long anfitrionId;
}
