import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { MenuModule } from 'primeng/menu';
import { ButtonModule } from 'primeng/button';
import { MenuItem } from 'primeng/api';

@Component({
  selector: 'app-menu',
  imports: [
    CommonModule,
    MenuModule,
    ButtonModule
  ],
  templateUrl: './menu.html',
  styleUrls: ['./menu.scss'],
})
export class Menu {
  menuItems: MenuItem[];
  authService: any;

  constructor(private router: Router) {
    this.menuItems = [
      {
        label: 'Mi cuenta',
        icon: 'pi pi-user',
        command: () => {
          this.goToAccount();
        }
      },
      {
        label: 'Ayuda y Soporte',
        icon: 'pi pi-question-circle',
        command: () => {
          this.goToSupport();
        }
      },
      {
        separator: true
      },
      {
        label: 'Cerrar sesión',
        icon: 'pi pi-sign-out',
        command: () => {
          this.logout();
        }
      }
    ];
  }

  goToAccount() {
    console.log('Ir a Mi cuenta');
    this.router.navigate(['/mi-cuenta']);
  }

  goToSupport() {
    console.log('Ir a Ayuda y Soporte');
    this.router.navigate(['/soporte']);
  }

  logout() {
    console.log('Cerrar sesión');
    // Lógica de logout
    this.authService.logout();
    this.router.navigate(['/login']);
  }

}
