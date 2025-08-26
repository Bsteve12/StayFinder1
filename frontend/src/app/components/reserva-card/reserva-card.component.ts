import { Component, Input, Output, EventEmitter } from '@angular/core';
import { Producto } from '../../models/producto';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-producto-card',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './producto-card.component.html',
  styleUrl: './producto-card.component.css'
})
export class ProductoCardComponent {
  @Input() producto!: Producto;
  @Output() productoAgregado = new EventEmitter<Producto>();

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
      this.productoAgregado.emit(this.producto);
    }
  }

  // 👇 ESTE es el método que hace la navegación
  goToDetail(): void {
    if (!this.producto || this.producto.id == null) return;
    console.log('Navegando al detalle de producto con ID:', this.producto.id); // 👈 para debug
    this.router.navigate(['/producto', this.producto.id]);
  }
}
