package com.stayFinder.proyectoFinal.repository;

import com.stayFinder.proyectoFinal.entity.Reserva;
import com.stayFinder.proyectoFinal.dto.outputDTO.ReservasPorUsuarioDTO;
import com.stayFinder.proyectoFinal.dto.outputDTO.IngresosPorAlojamientoDTO;
import com.stayFinder.proyectoFinal.dto.outputDTO.PublicacionesPendientesDTO;
import com.stayFinder.proyectoFinal.dto.outputDTO.UsuariosActivosDTO;
import com.stayFinder.proyectoFinal.dto.outputDTO.FavoritosPorUsuarioDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReportesRepository extends JpaRepository<Reserva, Long> {

    // 1) Cantidad de reservas por usuario
    @Query("SELECT new com.stayFinder.proyectoFinal.dto.outputDTO.ReservasPorUsuarioDTO(u.nombre, COUNT(r)) " +
            "FROM Reserva r JOIN r.usuario u " +
            "GROUP BY u.nombre")
    List<ReservasPorUsuarioDTO> obtenerReservasPorUsuario();

    // 2) Ingresos totales por alojamiento
    @Query("SELECT new com.stayFinder.proyectoFinal.dto.outputDTO.IngresosPorAlojamientoDTO(a.nombre, SUM(r.precioTotal)) " +
            "FROM Reserva r JOIN r.alojamiento a " +
            "GROUP BY a.nombre")
    List<IngresosPorAlojamientoDTO> obtenerIngresosPorAlojamiento();

    // 3) Publicaciones pendientes (usa el enum real)
    @Query("SELECT new com.stayFinder.proyectoFinal.dto.outputDTO.PublicacionesPendientesDTO(p.titulo, p.estado) " +
            "FROM Publicacion p " +
            "WHERE p.estado = com.stayFinder.proyectoFinal.entity.enums.EstadoSolicitudPublicacion.PENDIENTE")
    List<PublicacionesPendientesDTO> obtenerPublicacionesPendientes();

    // 4) Usuarios activos (tienen al menos 1 reserva) — agrupa por usuario
    @Query("SELECT new com.stayFinder.proyectoFinal.dto.outputDTO.UsuariosActivosDTO(u.nombre, u.email) " +
            "FROM Reserva r JOIN r.usuario u " +
            "GROUP BY u.id, u.nombre, u.email HAVING COUNT(r) > 0")
    List<UsuariosActivosDTO> obtenerUsuariosActivos();

    // 5) Favoritos por usuario
    @Query("SELECT new com.stayFinder.proyectoFinal.dto.outputDTO.FavoritosPorUsuarioDTO(u.nombre, a.nombre) " +
            "FROM Favorite f JOIN f.usuario u JOIN f.alojamiento a")
    List<FavoritosPorUsuarioDTO> obtenerFavoritosPorUsuario();
}
