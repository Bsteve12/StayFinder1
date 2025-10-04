package com.stayFinder.proyectoFinal.dto.outputDTO;

import com.stayFinder.proyectoFinal.entity.enums.EstadoReserva;
import com.stayFinder.proyectoFinal.entity.enums.TipoReserva;
import java.time.LocalDate;

public class ReservaHistorialDTO {

    private Long reservaId;
    private Long alojamientoId;
    private String alojamientoNombre;
    private Long usuarioId;
    private String usuarioNombre;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private int numeroHuespedes;
    private double precioTotal;
    private EstadoReserva estado;
    private TipoReserva tipoReserva;

    public ReservaHistorialDTO(Long reservaId, Long alojamientoId, String alojamientoNombre,
                               Long usuarioId, String usuarioNombre, LocalDate fechaInicio,
                               LocalDate fechaFin, int numeroHuespedes, double precioTotal,
                               EstadoReserva estado, TipoReserva tipoReserva) {
        this.reservaId = reservaId;
        this.alojamientoId = alojamientoId;
        this.alojamientoNombre = alojamientoNombre;
        this.usuarioId = usuarioId;
        this.usuarioNombre = usuarioNombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.numeroHuespedes = numeroHuespedes;
        this.precioTotal = precioTotal;
        this.estado = estado;
        this.tipoReserva = tipoReserva;
    }

    // Getters y Setters
    public Long getReservaId() { return reservaId; }
    public void setReservaId(Long reservaId) { this.reservaId = reservaId; }
    public Long getAlojamientoId() { return alojamientoId; }
    public void setAlojamientoId(Long alojamientoId) { this.alojamientoId = alojamientoId; }
    public String getAlojamientoNombre() { return alojamientoNombre; }
    public void setAlojamientoNombre(String alojamientoNombre) { this.alojamientoNombre = alojamientoNombre; }
    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
    public String getUsuarioNombre() { return usuarioNombre; }
    public void setUsuarioNombre(String usuarioNombre) { this.usuarioNombre = usuarioNombre; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }
    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }
    public int getNumeroHuespedes() { return numeroHuespedes; }
    public void setNumeroHuespedes(int numeroHuespedes) { this.numeroHuespedes = numeroHuespedes; }
    public double getPrecioTotal() { return precioTotal; }
    public void setPrecioTotal(double precioTotal) { this.precioTotal = precioTotal; }
    public EstadoReserva getEstado() { return estado; }
    public void setEstado(EstadoReserva estado) { this.estado = estado; }
    public TipoReserva getTipoReserva() { return tipoReserva; }
    public void setTipoReserva(TipoReserva tipoReserva) { this.tipoReserva = tipoReserva; }
}
