// src/app/services/auth.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { Router } from '@angular/router';

export interface LoginRequest {
  email: string;
  contrasena: string;
}

export interface LoginResponse {
  token: string;
}

export interface User {
  id?: number;
  nombre?: string;
  email?: string;
  role?: 'CLIENT' | 'OWNER' | 'ADMIN';
  foto?: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'https://stayfinder1-production.up.railway.app/api/usuario';

  private isAuthenticatedSubject = new BehaviorSubject<boolean>(false);
  private currentUserSubject = new BehaviorSubject<User | null>(null);

  public isAuthenticated$: Observable<boolean> = this.isAuthenticatedSubject.asObservable();
  public currentUser$: Observable<User | null> = this.currentUserSubject.asObservable();

  constructor(private http: HttpClient, private router: Router) {
    this.checkInitialAuth();
  }

  // Cargar sesión desde localStorage sin redirigir automáticamente
  private checkInitialAuth() {
    const token = localStorage.getItem('token');
    if (token) {
      const user = this.buildUserFromToken(token);
      if (user) {
        this.isAuthenticatedSubject.next(true);
        this.currentUserSubject.next(user);
        return;
      }
    }
    // Si no hay token válido, simplemente establecer estado no autenticado.
    this.isAuthenticatedSubject.next(false);
    this.currentUserSubject.next(null);
    // NO llamar a logout() ni navegar aquí. Logout debe ser llamado explícitamente (por ejemplo, al cerrar sesión).
  }

  // Login con backend (espera { token })
  login(credentials: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.apiUrl}/login`, credentials).pipe(
      tap((response) => {
        const token = response.token;
        if (!token) {
          throw new Error('No token recibido en login');
        }

        // Guardar token
        localStorage.setItem('token', token);

        // Construir user desde token
        const user = this.buildUserFromToken(token);
        if (user) {
          localStorage.setItem('user', JSON.stringify(user));
          localStorage.setItem('role', user.role || '');
          this.isAuthenticatedSubject.next(true);
          this.currentUserSubject.next(user);
        } else {
          // Si no podemos decodificar, limpiar e informar
          localStorage.removeItem('token');
          localStorage.removeItem('user');
          localStorage.removeItem('role');
          this.isAuthenticatedSubject.next(false);
          this.currentUserSubject.next(null);
        }
      })
    );
  }

  logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    localStorage.removeItem('role');
    this.isAuthenticatedSubject.next(false);
    this.currentUserSubject.next(null);
    // logout sí redirige
    this.router.navigate(['/login']);
  }

  getCurrentUser(): User | null {
    return this.currentUserSubject.value;
  }

  isAuthenticated(): boolean {
    return this.isAuthenticatedSubject.value;
  }

  getUserRole(): 'CLIENT' | 'OWNER' | 'ADMIN' | null {
    const user = this.getCurrentUser();
    return user?.role ?? null;
  }

  // Decodificar JWT (payload) en objeto User
  private buildUserFromToken(token: string): User | null {
    try {
      const payloadBase64 = token.split('.')[1];
      if (!payloadBase64) return null;
      const payloadJson = atob(payloadBase64.replace(/-/g, '+').replace(/_/g, '/'));
      const payload = JSON.parse(payloadJson);

      const role = (payload.role || payload.authorities || '').toString().toUpperCase();
      if (!['CLIENT', 'OWNER', 'ADMIN'].includes(role)) {
        // Si el backend devuelve roles distintos, aquí puedes ajustar la lógica.
        console.warn('Rol no reconocido en token:', role);
        // No devolvemos null para evitar bloquear; devolvemos role string solo si coincide.
        // Si quieres estrictitud, devuelve null aquí.
      }

      return {
        id: payload.usuarioId ?? payload.id ?? undefined,
        email: payload.email ?? payload.sub ?? undefined,
        nombre: payload.nombre ?? payload.name ?? payload.email ?? undefined,
        role: (['CLIENT', 'OWNER', 'ADMIN'].includes(role) ? (role as 'CLIENT' | 'OWNER' | 'ADMIN') : undefined)
      };
    } catch (error) {
      console.error('Error decodificando token JWT:', error);
      return null;
    }
  }
}
