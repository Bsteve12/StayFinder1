import { Routes } from '@angular/router';
import { ReservaListComponent } from './components/reserva-list/reserva-list.component';
import { ReservaDetailComponent } from './components/reserva-detail/reserva-detail.component';
import { GroupAuthComponent } from './components/group-auth/group-auth.component';
import { PasswordComponent } from './components/password/password.component';

export const routes: Routes = [
  // Redirige la raíz (http://localhost:4200) a /auth
  { path: '', pathMatch: 'full', redirectTo: 'auth' },

  { path: 'password', component: PasswordComponent, title: 'Recuperar Contraseña' },

  { path: 'auth', component: GroupAuthComponent, title: 'Iniciar Sesión' },

  // Página principal de productos
  { path: 'reservas', component: ReservaListComponent, title: 'Nuestros Productos' },

  // Página de detalle de producto por ID
  { path: 'reserva/:id', component: ReservaDetailComponent, title: 'Detalle de la reserva' },

  // Redirige cualquier ruta inválida a /login
  { path: '**', redirectTo: 'auth', pathMatch: 'full' }
];
