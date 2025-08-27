import { Routes } from '@angular/router';
import { ReservaListComponent } from './components/reserva-list/reserva-list.component';
import { ReservaDetailComponent } from './components/reserva-detail/reserva-detail.component';

export const routes: Routes = [
  // Redirige la raíz (http://localhost:4200) a /productos
  { path: '', pathMatch: 'full', redirectTo: 'reservas' },

  // Página principal de productos
  { path: 'reservas', component: ReservaListComponent, title: 'Nuestros Productos' },

  // Página de detalle de producto por ID
  { path: 'reserva/:id', component: ReservaDetailComponent, title: 'Detalle de la reserva' },

  // Redirige cualquier ruta inválida a /productos
  { path: '**', redirectTo: 'reservas' }
];
