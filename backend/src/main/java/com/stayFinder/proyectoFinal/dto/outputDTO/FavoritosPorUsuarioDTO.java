package com.stayFinder.proyectoFinal.dto.outputDTO;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FavoritosPorUsuarioDTO {
    private String nombreUsuario;
    private String alojamiento;
    private Long cantidadFavoritos;
}
