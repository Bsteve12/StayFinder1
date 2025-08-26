import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Producto } from '../models/reserva';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ProductoService {

  constructor(private http: HttpClient) {

    
   }

  // ✅ Corregido: tipamos productos como Producto[]
  getProductoById(id: number): Observable<Producto | undefined> {
    return this.getProductos().pipe(
      map((productos: Producto[]) => productos.find((p: Producto) => p.id === id))
    );
  }
  // Se actualizan los productos para que sean alojamientos reales en Colombia, con imágenes y precios en COP
  getProductos(): Observable<Producto[]> {
   
    
    return this.http.get<Producto[]>('http://localhost:8080/api/productos'); 
  
  }

  
}
