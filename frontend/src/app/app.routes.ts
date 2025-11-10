import { Routes } from '@angular/router';
import { Soporte } from './soporte/soporte';
import { Inicio } from './inicio/inicio';

export const routes: Routes = [
    { path: 'soporte', component: Soporte },
    { path: 'inicio', component: Inicio },

];
