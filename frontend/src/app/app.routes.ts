import { Routes } from '@angular/router';
import { ProductoListComponent } from './components/producto-list/producto-list.component';
import { ProductoDetailComponent } from './components/producto-detail/producto-detail.component';

export const routes: Routes = [
  // Redirige la raíz (http://localhost:4200) a /productos
  { path: '', pathMatch: 'full', redirectTo: 'productos' },

  // Página principal de productos
  { path: 'productos', component: ProductoListComponent, title: 'Nuestros Productos' },

  // Página de detalle de producto por ID
  { path: 'producto/:id', component: ProductoDetailComponent, title: 'Detalle de producto' },

  // Redirige cualquier ruta inválida a /productos
  { path: '**', redirectTo: 'productos' }
];
