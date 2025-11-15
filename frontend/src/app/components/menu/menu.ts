import { Component, OnInit, Input } from '@angular/core';
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
export class Menu implements OnInit {
  @Input() isAuthenticated: boolean = false;
  @Input() userRole: 'CLIENT' | 'OWNER' | 'ADMIN' | null = null;
  @Input() userName: string = '';
  @Input() userPhoto: string = '';
  
  menuItems: MenuItem[] = [];

  constructor(private router: Router) {}

  ngOnInit() {
    this.setupMenuItems();
  }

  setupMenuItems() {
    if (!this.isAuthenticated) {
      // Usuario NO autenticado
      this.menuItems = [
        {
          label: 'Iniciar sesión',
          icon: 'pi pi-sign-in',
          command: () => {
            this.goToLogin();
          }
        },
        {
          label: 'Registrarse',
          icon: 'pi pi-user-plus',
          command: () => {
            this.goToRegister();
          }
        },
        {
          separator: true
        },
        {
          label: 'Ayuda y Soporte',
          icon: 'pi pi-question-circle',
          command: () => {
            this.goToSupport();
          }
        }
      ];
    } else {
      // Usuario AUTENTICADO
      this.menuItems = [
        {
          label: 'Mi cuenta',
          icon: 'pi pi-user',
          command: () => {
            this.goToAccount();
          }
        }
      ];

      // Agregar opciones según el rol
      if (this.userRole === 'CLIENT') {
        this.menuItems.push(
          {
          label: 'Ayuda y Soporte',
          icon: 'pi pi-question-circle',
          command: () => {
            this.goToSupport();
          }
        }
        );
      } else if (this.userRole === 'OWNER') {
        this.menuItems.push(
          {
            label: 'Dashboard Anfitrión',
            icon: 'pi pi-chart-line',
            command: () => {
              this.router.navigate(['/anfitrion']);
            }
          },
          {
          label: 'Ayuda y Soporte',
          icon: 'pi pi-question-circle',
          command: () => {
            this.goToSupport();
          }
        }
        );
      } else if (this.userRole === 'ADMIN') {
        this.menuItems.push(
          {
            label: 'Panel Admin',
            icon: 'pi pi-shield',
            command: () => {
              this.router.navigate(['/administrador']);
            }
          },
          {
          label: 'Ayuda y Soporte',
          icon: 'pi pi-question-circle',
          command: () => {
            this.goToSupport();
          }
        }
        );
      }

      // Opciones comunes para todos los autenticados
      this.menuItems.push(
        {
          separator: true
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
          styleClass: 'logout-item',
          command: () => {
            this.logout();
          }
        }
      );
    }
  }

  goToLogin() {
    this.router.navigate(['/login']);
  }

  goToRegister() {
    this.router.navigate(['/register']);
  }

  goToAccount() {
    // Redirigir según el rol
    if (this.userRole === 'CLIENT') {
      this.router.navigate(['/mi-cuenta']);
    } else if (this.userRole === 'OWNER') {
      this.router.navigate(['/anfitrion']);
    } else if (this.userRole === 'ADMIN') {
      this.router.navigate(['/administrador']);
    } 
  }

  goToSupport() {
    this.router.navigate(['/soporte']);
  }

  logout() {
    // Lógica de logout
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    localStorage.removeItem('role');
    
    // Redirigir al login
    this.router.navigate(['/login']);
    
    // Opcional: Mostrar mensaje
    alert('Sesión cerrada exitosamente');
  }

}
