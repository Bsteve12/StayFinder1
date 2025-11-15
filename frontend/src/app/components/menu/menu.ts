// src/app/components/menu/menu.ts
import { Component, OnInit, Input, OnChanges, SimpleChanges } from '@angular/core';
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
export class Menu implements OnInit, OnChanges {
  @Input() isAuthenticated: boolean = false;
  @Input() userRole: 'CLIENT' | 'OWNER' | 'ADMIN' | null = null;
  @Input() userName: string = '';
  @Input() userPhoto: string = '';

  menuItems: MenuItem[] = [];

  constructor(private router: Router) {}

  ngOnInit() {
    // inicializar menú
    this.setupMenuItems();
  }

  ngOnChanges(changes: SimpleChanges) {
    // Si cambió el estado de autenticación o el usuario, reconstruir el menú.
    if (changes['isAuthenticated'] || changes['userRole'] || changes['userName'] || changes['userPhoto']) {
      this.setupMenuItems();
    }
  }

  setupMenuItems() {
    // ---------- USUARIO NO AUTENTICADO (igual que tenías) ----------
    if (!this.isAuthenticated) {
      this.menuItems = [
        {
          label: 'Iniciar sesión',
          icon: 'pi pi-sign-in',
          command: () => { this.goToLogin(); }
        },
        {
          label: 'Registrarse',
          icon: 'pi pi-user-plus',
          command: () => { this.goToRegister(); }
        },
        { separator: true },
        {
          label: 'Ayuda y Soporte',
          icon: 'pi pi-question-circle',
          command: () => { this.goToSupport(); }
        }
      ];
      return;
    }

    // ---------- USUARIO AUTENTICADO: menú simple con nombre + Mi cuenta / Soporte ----------
    // Primer item: un "header" no clickeable que muestra el nombre (estilizado con class)
    const headerLabel = this.userName ? this.userName : 'Usuario';

    this.menuItems = [
      {
        label: headerLabel,
        icon: this.userPhoto ? undefined : 'pi pi-user', // si tienes foto, el avatar ya aparece en el DOM (menu.html)
        // deshabilitado para que sea solo informativo
        disabled: true,
        styleClass: 'menu-user-header'
      },
      { separator: true },
      {
        label: 'Mi cuenta',
        icon: 'pi pi-user',
        command: () => { this.goToAccount(); }
      },
      {
        label: 'Ayuda y Soporte',
        icon: 'pi pi-question-circle',
        command: () => { this.goToSupport(); }
      },
      { separator: true },
      {
        label: 'Cerrar sesión',
        icon: 'pi pi-sign-out',
        styleClass: 'logout-item',
        command: () => { this.logout(); }
      }
    ];

    // ---- Si más adelante quieres items según rol, puedes añadirlos aquí (ejemplo comentado) ----
    // if (this.userRole === 'OWNER') { ... }
    // if (this.userRole === 'ADMIN') { ... }
  }

  // Rutas / acciones (mantengo exactamente tus funciones)
  goToLogin() {
    this.router.navigate(['/login']);
  }

  goToRegister() {
    this.router.navigate(['/register']);
  }

  goToAccount() {
    // Redirigir según el rol (si en el futuro quieres redirigir distinto por rol)
    if (this.userRole === 'CLIENT') {
      this.router.navigate(['/mi-cuenta']);
    } else if (this.userRole === 'OWNER') {
      this.router.navigate(['/anfitrion']);
    } else if (this.userRole === 'ADMIN') {
      this.router.navigate(['/administrador']);
    } else {
      // fallback
      this.router.navigate(['/mi-cuenta']);
    }
  }

  goToSupport() {
    this.router.navigate(['/soporte']);
  }

  logout() {
    // Lógica de logout: limpiar y redirigir al login
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    localStorage.removeItem('role');

    // Si tienes un AuthService con observable sería mejor llamarlo aquí, pero mantengo tu lógica simple:
    this.router.navigate(['/login']);
    alert('Sesión cerrada exitosamente');
  }
}
