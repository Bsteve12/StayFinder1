import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AlojamientosService {
  
  ruta = "http://localhost:8080/api/alojamientos";

  constructor(private http: HttpClient) {}

  obtenerAlojamientosActivos(): Observable<any> {
    return this.http.get(`${this.ruta}/activos`);
  }

  obtenerAlojamientoPorId(id: number): Observable<any> {
    return this.http.get(`${this.ruta}/${id}`);
  }

}
