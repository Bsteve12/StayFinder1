package com.stayFinder.proyectoFinal.dto.outputDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Respuesta con los datos de la revisión de una solicitud de Owner")
public class RespuestaSolicitudDTO {
    @Schema(description = "ID de la solicitud evaluada", example = "75")
    private Long solicitudId;

    @Schema(description = "ID del administrador que evaluó la solicitud", example = "3")
    private Long adminId;

    @Schema(description = "Indica si la solicitud fue aprobada", example = "true")
    private boolean aprobada;

    @Schema(description = "Comentario del administrador", example = "Solicitud aprobada, cumple con los requisitos")
    private String comentario;
}
