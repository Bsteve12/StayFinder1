package com.stayFinder.proyectoFinal.dto.inputDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import jakarta.validation.constraints.NotBlank;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Datos de entrada para registrar un pago en línea")
public class PagoInputDTO {
    @NotNull
    @Schema(description = "ID de la reserva asociada al pago", example = "15")
    private Long reservaId;

    @NotBlank
    @Schema(description = "Método de pago utilizado", example = "Tarjeta de Crédito")
    private String metodoPago;

    @NotNull
    @Schema(description = "Monto total pagado", example = "250.75")
    private Double monto;
}
