import { Routes } from '@angular/router';
import { Soporte } from './soporte/soporte';
import { Inicio } from './inicio/inicio';
import { Detalle } from './detalle/detalle';
import { Login } from './login/login';

export const routes: Routes = [
    { path: '', component: Inicio },
    { path: 'soporte', component: Soporte },
    { path: 'inicio', component: Inicio },
    { path: 'detalle/:id', component: Detalle },
    { path: 'login', component: Login }

];
