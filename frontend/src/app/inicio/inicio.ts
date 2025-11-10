import { Component, inject, Inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CarouselModule } from 'primeng/carousel';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { InputNumberModule } from 'primeng/inputnumber';
import { FormsModule, FormControl, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatDialog, MatDialogModule, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { provideNativeDateAdapter, MAT_DATE_FORMATS, MAT_NATIVE_DATE_FORMATS } from '@angular/material/core';

interface Destination {
  id: number;
  name: string;
  image: string;
  location: string;
  price: number;
  rating: number;
  favorite: boolean;
}

interface Testimonial {
  id: number;
  name: string;
  avatar: string;
  comment: string;
  rating: number;
  date: string;
}

@Component({
  selector: 'app-inicio',
  imports: [
    CommonModule,
    CarouselModule,
    ButtonModule,
    InputTextModule,
    InputNumberModule,
    FormsModule,
    MatButtonModule
  ],
  providers: [provideNativeDateAdapter()],
  templateUrl: './inicio.html',
  styleUrl: './inicio.scss',
})
export class Inicio {
   dialog = inject(MatDialog);
  
  // Datos del formulario de búsqueda
  searchLocation: string = '';
  checkInDate: Date | null = null;
  checkOutDate: Date | null = null;
  guests: number = 1;

  // Destinos populares
  destinations: Destination[] = [
    {
      id: 1,
      name: 'Casa en la Playa',
      image: 'https://images.unsplash.com/photo-1499793983690-e29da59ef1c2?w=400',
      location: 'Cartagena, Colombia',
      price: 150000,
      rating: 4.8,
      favorite: false
    },
    {
      id: 2,
      name: 'Apartamento Moderno',
      image: 'https://images.unsplash.com/photo-1502672260266-1c1ef2d93688?w=400',
      location: 'Bogotá, Colombia',
      price: 120000,
      rating: 4.6,
      favorite: false
    },
    {
      id: 3,
      name: 'Cabaña en la Montaña',
      image: 'https://images.unsplash.com/photo-1518780664697-55e3ad937233?w=400',
      location: 'Eje Cafetero, Colombia',
      price: 180000,
      rating: 4.9,
      favorite: false
    },
    {
      id: 4,
      name: 'Villa Campestre',
      image: 'https://images.unsplash.com/photo-1564013799919-ab600027ffc6?w=400',
      location: 'Medellín, Colombia',
      price: 200000,
      rating: 5.0,
      favorite: false
    },
    {
      id: 5,
      name: 'Loft en el Centro',
      image: 'https://images.unsplash.com/photo-1522708323590-d24dbb6b0267?w=400',
      location: 'Cali, Colombia',
      price: 95000,
      rating: 4.5,
      favorite: false
    }
  ];

  // Testimonios
  testimonials: Testimonial[] = [
    {
      id: 1,
      name: 'María González',
      avatar: 'https://i.pravatar.cc/150?img=1',
      comment: 'Excelente experiencia, el lugar superó mis expectativas. La ubicación era perfecta y la atención al cliente fue increíble.',
      rating: 5,
      date: 'Hace 2 semanas'
    },
    {
      id: 2,
      name: 'Carlos Rodríguez',
      avatar: 'https://i.pravatar.cc/150?img=3',
      comment: 'Muy recomendado, todo estuvo perfecto. El apartamento estaba impecable y tenía todas las comodidades necesarias.',
      rating: 5,
      date: 'Hace 1 mes'
    },
    {
      id: 3,
      name: 'Ana Martínez',
      avatar: 'https://i.pravatar.cc/150?img=5',
      comment: 'Un lugar hermoso y acogedor. Definitivamente volveré. La vista era espectacular y la zona muy tranquila.',
      rating: 5,
      date: 'Hace 3 semanas'
    },
    {
      id: 4,
      name: 'Juan Pérez',
      avatar: 'https://i.pravatar.cc/150?img=7',
      comment: 'Perfecta para unas vacaciones en familia. Todos disfrutamos muchísimo nuestra estadía.',
      rating: 4,
      date: 'Hace 1 semana'
    },
    {
      id: 5,
      name: 'Laura Sánchez',
      avatar: 'https://i.pravatar.cc/150?img=9',
      comment: 'Increíble atención y un lugar mágico. Sin duda la mejor opción para descansar y relajarse.',
      rating: 5,
      date: 'Hace 4 días'
    }
  ];

  // Opciones del carrusel
  responsiveOptions = [
    {
      breakpoint: '1400px',
      numVisible: 4,
      numScroll: 1
    },
    {
      breakpoint: '1200px',
      numVisible: 3,
      numScroll: 1
    },
    {
      breakpoint: '768px',
      numVisible: 2,
      numScroll: 1
    },
    {
      breakpoint: '560px',
      numVisible: 1,
      numScroll: 1
    }
  ];

  toggleFavorite(destination: Destination) {
    destination.favorite = !destination.favorite;
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
  }
}

// Dialog Component para el DatePicker
@Component({
  selector: 'date-picker-dialog',
  standalone: true,
  imports: [
    CommonModule,
    MatDatepickerModule,
    MatDialogModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    ReactiveFormsModule,
  ],
  providers: [
    provideNativeDateAdapter(),
    { provide: MAT_DATE_FORMATS, useValue: MAT_NATIVE_DATE_FORMATS },
  ],
  template: `
    <div class="dialog-container">
      <h2 mat-dialog-title>{{ data.title }}</h2>
      <mat-dialog-content>
        <mat-form-field>
          <mat-label>Selecciona una fecha</mat-label>
          <input matInput [matDatepicker]="picker" [formControl]="date">
          <mat-datepicker-toggle matIconSuffix [for]="picker"></mat-datepicker-toggle>
          <mat-datepicker #picker></mat-datepicker>
        </mat-form-field>
      </mat-dialog-content>
      <mat-dialog-actions align="end">
        <button mat-button (click)="onCancel()">Cancelar</button>
        <button mat-raised-button color="primary" (click)="onConfirm()">Confirmar</button>
      </mat-dialog-actions>
    </div>
  `,
  styles: [`
    .dialog-container {
      padding: 20px;
      min-width: 300px;
    }
    
    mat-form-field {
      width: 100%;
      margin-top: 16px;
    }
    
    mat-dialog-actions {
      margin-top: 24px;
      padding-top: 16px;
    }
  `]
})
export class DatePickerDialog {
  dialogRef = inject<MatDialogRef<DatePickerDialog>>(MatDialogRef<DatePickerDialog>);
  data = inject(MAT_DIALOG_DATA);
  readonly date = new FormControl(new Date());

  constructor() {
    if (this.data.selectedDate) {
      this.date.setValue(this.data.selectedDate);
    }
  }

  onCancel() {
    this.dialogRef.close();
  }

  onConfirm() {
    this.dialogRef.close(this.date.value);
  }
}
