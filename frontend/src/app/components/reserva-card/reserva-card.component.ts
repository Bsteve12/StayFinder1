import { Component, Input, Output, EventEmitter } from '@angular/core';
import { Reserva } from '../../models/reserva';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-reserva-card',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './reserva-card.component.html',
  styleUrls: ['./reserva-card.component.css']
})
export class ReservaCardComponent {
  @Input() reserva!: Reserva;
  @Output() reservaAgregado = new EventEmitter<Reserva>();

  esFavorito = false;
  enCarrito = false;

  constructor(private router: Router) {}  // 👈 IMPORTANTE

  toggleFavorito(): void {
    this.esFavorito = !this.esFavorito;
  }

  agregarAlCarrito(event: Event): void {
    event.stopPropagation();
    this.enCarrito = !this.enCarrito;
    if (this.enCarrito) {
      this.reservaAgregado.emit(this.reserva);
    }
  }

  // 👇 ESTE es el método que hace la navegación
  goToDetail(): void {
    if (!this.reserva || this.reserva.id == null) return;
    console.log('Navegando al detalle de producto con ID:', this.reserva.id); // 👈 para debug
    this.router.navigate(['/reserva', this.reserva.id]);
  }
}
