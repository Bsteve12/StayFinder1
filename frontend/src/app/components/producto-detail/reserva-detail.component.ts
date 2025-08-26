import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { Producto } from '../../models/reserva';
import { ProductoService } from '../../services/reserva.service';

@Component({
  selector: 'app-producto-detail',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './producto-detail.component.html',
  styleUrls: ['./producto-detail.component.css']   // 👈 debe ser styleUrls (plural)
})
export class ProductoDetailComponent implements OnInit {
  producto?: Producto;
  loading = true;
  notFound = false;

  constructor(
    private route: ActivatedRoute,
    private productoService: ProductoService
  ) {}

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');
    const id = Number(idParam);

    if (Number.isNaN(id)) {
      this.notFound = true;
      this.loading = false;
      return;
    }

    this.productoService.getProductoById(id).subscribe(p => {
      this.producto = p;
      this.notFound = !p;
      this.loading = false;
    });
  }
}
