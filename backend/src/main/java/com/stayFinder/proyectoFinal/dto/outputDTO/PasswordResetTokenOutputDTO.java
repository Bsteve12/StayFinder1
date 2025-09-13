package com.stayFinder.proyectoFinal.dto.outputDTO;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PasswordResetTokenOutputDTO {
    private Long id;
    private Long usuarioId;
    private String token;
    private LocalDateTime expiresAt;
    private boolean usado;
}
