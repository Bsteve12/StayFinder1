package com.stayFinder.proyectoFinal.dto.outputDTO;

import lombok.*;
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FavoritosPorUsuarioResponseDTO {

    private String nombreUsuario;
    private String alojamiento;
    private Long cantidadFavoritos;

    // 🔹 Constructor adicional para consultas que solo devuelven nombreUsuario y alojamiento
    public FavoritosPorUsuarioResponseDTO(String nombreUsuario, String alojamiento) {
        this.nombreUsuario = nombreUsuario;
        this.alojamiento = alojamiento;
    }
}
