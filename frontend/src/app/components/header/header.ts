
import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from "@angular/router";
import { CarouselModule } from 'primeng/carousel';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { InputNumberModule } from 'primeng/inputnumber';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatDialog } from '@angular/material/dialog';
import { DatePickerDialog } from '../date-picker-dialog/date-picker-dialog';
import { Menu } from "../menu/menu";

@Component({
  selector: 'app-header',
  imports: [
    CommonModule,
    CarouselModule,
    ButtonModule,
    InputTextModule,
    InputNumberModule,
    FormsModule,
    MatButtonModule,
    RouterModule,
    Menu
],
  templateUrl: './header.html',
  styleUrl: './header.scss',
})
export class Header implements OnInit {
  dialog = inject(MatDialog);

  // Estado de autenticación
  isAuthenticated: boolean = false;
  userRole: 'CLIENT' | 'OWNER' | 'ADMIN' | null = null;
  userName: string = '';
  userPhoto: string = '';

  // Datos del formulario de búsqueda
  searchLocation: string = '';
  checkInDate: Date | null = null;
  checkOutDate: Date | null = null;
  guests: number = 1;

  constructor(private router: Router) {}

  ngOnInit() {
    this.checkAuthentication();
  }

  // Verificar si el usuario está autenticado
  checkAuthentication() {
    const token = localStorage.getItem('token');
    const user = localStorage.getItem('user');
    const role = localStorage.getItem('role');

    if (token && user) {
      this.isAuthenticated = true;
      this.userRole = (role as 'CLIENT' | 'OWNER' | 'ADMIN') || 'CLIENT';
      
      // Parsear datos del usuario
      try {
        const userData = JSON.parse(user);
        this.userName = userData.nombre || userData.name || 'Usuario';
        this.userPhoto = userData.foto || userData.photo || '';
      } catch (error) {
        console.error('Error al parsear usuario:', error);
        this.userName = 'Usuario';
      }
    } else {
      this.isAuthenticated = false;
      this.userRole = null;
    }
  }

  // Manejar clic en "Conviértete en Anfitrión"
  onBecomeHost() {
    if (!this.isAuthenticated) {
      // Usuario NO autenticado: redirigir al login con mensaje
      alert('Debes iniciar sesión para convertirte en anfitrión.\n\nRequisitos:\n• Fotocopia de cédula\n• Documento de propiedad del alojamiento\n• Otros documentos requeridos');
      this.router.navigate(['/login'], { 
        queryParams: { returnUrl: '/conviertete-anfitrion' } 
      });
    } else {
      // Usuario AUTENTICADO: ir a la página de convertirse en anfitrión
      this.router.navigate(['/conviertete-anfitrion']);
    }
  }

  openCheckInDialog() {
    const dialogRef = this.dialog.open(DatePickerDialog, {
      data: { selectedDate: this.checkInDate, title: 'Selecciona fecha de Check-in' }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.checkInDate = result;
      }
    });
  }

  openCheckOutDialog() {
    const dialogRef = this.dialog.open(DatePickerDialog, {
      data: { selectedDate: this.checkOutDate, title: 'Selecciona fecha de Check-out' }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.checkOutDate = result;
      }
    });
  }

  formatDate(date: Date | null): string {
    if (!date) return 'Agrega fecha';
    return date.toLocaleDateString('es-ES', { day: '2-digit', month: '2-digit', year: 'numeric' });
  }

  onSearch() {
    console.log('Búsqueda:', {
      location: this.searchLocation,
      checkIn: this.checkInDate,
      checkOut: this.checkOutDate,
      guests: this.guests
    });
    
    // Aquí puedes agregar la lógica de búsqueda real
    // Por ejemplo, navegar a una página de resultados con los filtros
    this.router.navigate(['/buscar'], {
      queryParams: {
        ubicacion: this.searchLocation,
        checkIn: this.checkInDate?.toISOString(),
        checkOut: this.checkOutDate?.toISOString(),
        guests: this.guests
      }
    });
  }

}