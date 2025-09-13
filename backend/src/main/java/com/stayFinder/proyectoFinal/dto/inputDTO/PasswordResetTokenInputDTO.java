package com.stayFinder.proyectoFinal.dto.inputDTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PasswordResetTokenInputDTO {
    private Long usuarioId;
    private String token;
}
