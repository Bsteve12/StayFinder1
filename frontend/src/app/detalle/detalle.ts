import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CarouselModule } from 'primeng/carousel';
import { ButtonModule } from 'primeng/button';
import { GalleriaModule } from 'primeng/galleria';
import { Header } from "../components/header/header";
import { AlojamientosService } from '../services/alojamientos';
import { ActivatedRoute, Route } from '@angular/router';

interface AccommodationImage {
  url: string;
  alt: string;
}

interface AccommodationDetail {
  id: number;
  title: string;
  location: string;
  bedrooms: number;
  bathrooms: string;
  guests: number;
  price: number;
  rating: number;
  reviews: number;
  description: string;
  amenities: string[];
  images: AccommodationImage[];
}

@Component({
  selector: 'app-detalle',
  imports: [CommonModule, CarouselModule, ButtonModule, GalleriaModule, Header],
  templateUrl: './detalle.html',
  styleUrl: './detalle.scss',
})
export class Detalle {

  alojamientoId = signal<string>('');
  alojamiento:any;

  constructor(private route: ActivatedRoute , private alojamientosService: AlojamientosService) {
    this.route.paramMap.subscribe(params => {
      this.alojamientoId.set(params.get('id') || '');
      this.getById(this.alojamientoId());
    });
  }

  getById(id:string) {
    this.alojamientosService.obtenerAlojamientoPorId(Number(id)).subscribe({
      next: (data) => {
        this.alojamiento = data;
      },
      error: (error) => {
        console.error('Error al obtener alojamiento por ID:', error);
      }
    });
  }

  accommodation: AccommodationDetail = {
    id: 1,
    title: 'Habitación amoblada laureles 6',
    location: 'Medellín, Colombia',
    bedrooms: 2,
    bathrooms: 'Baño compartido',
    guests: 4,
    price: 150000,
    rating: 4.8,
    reviews: 127,
    description: 'Hermosa habitación amoblada en el corazón de Laureles, uno de los barrios más seguros y vibrantes de Medellín. Perfecta para viajeros que buscan comodidad y una excelente ubicación cerca de restaurantes, cafés y transporte público.',
    amenities: [
      'WiFi de alta velocidad',
      'Cocina equipada',
      'Aire acondicionado',
      'TV con cable',
      'Lavadora',
      'Zona de trabajo',
      'Estacionamiento gratuito',
      'Seguridad 24/7'
    ],
    images: [
      {
        url: 'https://images.unsplash.com/photo-1522708323590-d24dbb6b0267?w=800',
        alt: 'Vista principal'
      },
      {
        url: 'https://images.unsplash.com/photo-1502672260266-1c1ef2d93688?w=800',
        alt: 'Sala de estar'
      },
      {
        url: 'https://images.unsplash.com/photo-1560448204-e02f11c3d0e2?w=800',
        alt: 'Habitación'
      },
      {
        url: 'https://images.unsplash.com/photo-1556912173-3bb406ef7e77?w=800',
        alt: 'Cocina'
      },
      {
        url: 'https://images.unsplash.com/photo-1564013799919-ab600027ffc6?w=800',
        alt: 'Baño'
      }
    ]
  };

  responsiveOptions = [
    {
      breakpoint: '1024px',
      numVisible: 5
    },
    {
      breakpoint: '768px',
      numVisible: 3
    },
    {
      breakpoint: '560px',
      numVisible: 1
    }
  ];

  onReserve() {
    console.log('Reservando alojamiento:', this.accommodation.id);
    // Aquí iría la lógica de reserva
  }

  onBack() {
    console.log('Volver atrás');
    // Aquí iría la navegación hacia atrás
  }

}
