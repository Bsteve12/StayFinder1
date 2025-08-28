import { Routes } from '@angular/router';
import { ReservaListComponent } from './components/reserva-list/reserva-list.component';
import { ReservaDetailComponent } from './components/reserva-detail/reserva-detail.component';
import { LoginComponent } from './components/auth/login/login.component';
import { RegisterComponent } from './components/auth/register/register.component';
import { PasswordComponent } from './components/password/password.component';

export const routes: Routes = [
  // Redirige la raíz (http://localhost:4200) a /login
  { path: '', pathMatch: 'full', redirectTo: 'login' },

  // Página de inicio de sesión
  { path: 'login', component: LoginComponent, title: 'Iniciar Sesión' },

  // Página de registro
  { path: 'register', component: RegisterComponent, title: 'Registrarse' },

  // Página de recuperación de contraseña
  { path: 'password', component: PasswordComponent, title: 'Recuperar Contraseña' },

  // Página principal de productos
  { path: 'reservas', component: ReservaListComponent, title: 'Nuestros Productos' },

  // Página de detalle de producto por ID
  { path: 'reserva/:id', component: ReservaDetailComponent, title: 'Detalle de la reserva' },

  // Redirige cualquier ruta inválida a /login
  { path: '**', redirectTo: '/auth/login', pathMatch: 'full' }
];
