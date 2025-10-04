package com.stayFinder.proyectoFinal.dto.outputDTO;

import com.stayFinder.proyectoFinal.entity.enums.EstadoSolicitudPublicacion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublicacionesPendientesDTO {
    private String titulo;
    private EstadoSolicitudPublicacion estado;
}
