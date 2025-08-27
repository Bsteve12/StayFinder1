import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { HttpClient } from '@angular/common/http';
import { Reserva } from '../models/reserva';

@Injectable({
  providedIn: 'root'
})
export class ReservaService {

  private apiUrl = 'http://localhost:8080/api/reserva'; // Asegúrate que coincide con tu backend

  constructor(private http: HttpClient) { }

  // Obtener todas las reservas
  getReservas(): Observable<Reserva[]> {
    return this.http.get<Reserva[]>(this.apiUrl);
  }

  // Obtener una reserva por ID
  getReservaById(id: number): Observable<Reserva | undefined> {
    // Puedes usar esta opción si quieres filtrar en frontend
    return this.getReservas().pipe(
      map((reservas: Reserva[]) => reservas.find((r: Reserva) => r.id === id))
    );

    // O si tu backend soporta GET por ID, usar:
    // return this.http.get<Reserva>(`${this.apiUrl}/${id}`);
  }
}
