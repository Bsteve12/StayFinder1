import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CarouselModule } from 'primeng/carousel';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { InputNumberModule } from 'primeng/inputnumber';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatDialog } from '@angular/material/dialog';
import { provideNativeDateAdapter } from '@angular/material/core';
import { RouterLink, RouterModule } from "@angular/router";
import { DatePickerDialog } from '../date-picker-dialog/date-picker-dialog';

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
    RouterModule
  ],
  templateUrl: './header.html',
  styleUrl: './header.scss',
})
export class Header {
  
   dialog = inject(MatDialog);

  // Datos del formulario de búsqueda
  searchLocation: string = '';
  checkInDate: Date | null = null;
  checkOutDate: Date | null = null;
  guests: number = 1;

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
  }

}