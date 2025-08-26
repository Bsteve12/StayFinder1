import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReservaService } from '../../services/reserva.service';
import { ReservaCardComponent } from '../reserva-card/reserva-card.component';
import { Reserva } from '../../models/reserva';

@Component({
  selector: 'app-reserva-list',
  standalone: true,
  imports: [CommonModule, ReservaCardComponent],
  templateUrl: './reserva-list.component.html',
  styleUrl: './reserva-list.component.css'
})
export class ReservaListComponent implements OnInit {
  reserva: Reserva[] = [];
  reservaFiltrados: Reserva[] = [];
  reservaEnCarrito: Reserva[] = [];
  loading = true;
  error = false;
  filtroActual = '';
  terminoBusqueda = '';

  constructor(private reservaService: ReservaService) {}

  ngOnInit(): void {
    this.cargarReservas();
  }

  cargarReservas(): void {
    this.reservaService.getReserva().subscribe({
      next: (reservas) =>  {
        this.reservas = reservas;
        this.reservasFiltradas = reservas;
        this.loading = false;
      },
      error: (error) => {
        console.error('Error al cargar reservas:', error);
        this.error = true;
        this.loading = false;
      }
    });
  }

  filtrarPorPrecio(event: Event): void {
    const target = event.target as HTMLSelectElement;
    this.filtroActual = target.value;
    this.aplicarFiltros();
  }

  limpiarFiltros(): void {
    this.filtroActual = '';
    this.terminoBusqueda = '';
    this.reservasFiltradas = this.reservas;
  }

  agregarAlCarrito(reserva: Reserva): void {
    if (!this.reservasEnCarrito.find(p => p.id === reserva.id)) {
      this.reservasEnCarrito.push(reserva);
    }
  }

  calcularTotal(): number {
    return this.reservasEnCarrito.reduce((total, reserva) => total + reserva.precio, 0);
  }
  trackById(index: number, reserva: Reserva): number {
    return reserva.id;
}

  onBuscar(event: Event): void {
    const input = event.target as HTMLInputElement;
    this.terminoBusqueda = input.value.toLowerCase();
    this.aplicarFiltros();
  }

  private aplicarFiltros(): void {
    let lista = [...this.reservas];

    if (this.filtroActual === 'bajo') {
      lista = lista.filter(p => p.precio < 500);
    } else if (this.filtroActual === 'medio') {
      lista = lista.filter(p => p.precio >= 500 && p.precio <= 1000);
    } else if (this.filtroActual === 'alto') {
      lista = lista.filter(p => p.precio > 1000);
    }

    if (this.terminoBusqueda.trim().length > 0) {
      lista = lista.filter(p => p.nombre?.toLowerCase().includes(this.terminoBusqueda));
    }

    this.reservasFiltradas = lista;
  }
}