package com.stayFinder.proyectoFinal.dto.inputDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Datos de entrada para solicitar convertirse en anfitrión (owner)")
public class SolicitudOwnerDTO {
    @NotNull
    @Schema(description = "ID del usuario que solicita convertirse en anfitrión", example = "5")
    private Long usuarioId;

    @Schema(description = "Comentario adicional del solicitante", example = "Tengo varios alojamientos disponibles para arrendar")
    private String comentario;
}
