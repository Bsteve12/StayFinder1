import { Routes } from '@angular/router';
import { Soporte } from './soporte/soporte';
import { Inicio } from './inicio/inicio';
import { Detalle } from './detalle/detalle';
import { Login } from './login/login';
import { Register } from './register/register';
import { Password } from './password/password';
import { MiCuenta } from './mi-cuenta/mi-cuenta';
import { Anfitrion } from './anfitrion/anfitrion';

export const routes: Routes = [
    { path: '', component: Inicio },
    { path: 'soporte', component: Soporte },
    { path: 'inicio', component: Inicio },
    { path: 'detalle/:id', component: Detalle },
    { path: 'login', component: Login },
    { path: 'register', component: Register },
    { path: 'forgot-password', component: Password },
    { path: 'mi-cuenta', component: MiCuenta },
    { path: 'anfitrion', component: Anfitrion }

];
