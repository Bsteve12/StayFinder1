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
  id: number;
  nombre: string;
  email: string;
  role: 'CLIENT' | 'OWNER' | 'ADMIN';
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

  // -------------------------------
  // 🔐 Verificar sesión al recargar
  // -------------------------------
  private checkInitialAuth() {
    const token = localStorage.getItem('token');
    if (token) {
      const user = this.buildUserFromToken(token);
      if (user) {
        this.isAuthenticatedSubject.next(true);
        this.currentUserSubject.next(user);
      }
    }
  }

  // -------------------------------
  // 🔑 Login con API real
  // -------------------------------
  login(credentials: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.apiUrl}/login`, credentials).pipe(
      tap((response) => {
        // Guardar token
        localStorage.setItem('token', response.token);

        // Reconstruir usuario desde el token
        const user = this.buildUserFromToken(response.token);
        if (user) {
          localStorage.setItem('user', JSON.stringify(user));
          localStorage.setItem('role', user.role);
          this.isAuthenticatedSubject.next(true);
          this.currentUserSubject.next(user);
        }
      })
    );
  }

  // -------------------------------
  // 🚪 Logout
  // -------------------------------
  logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    localStorage.removeItem('role');

    this.isAuthenticatedSubject.next(false);
    this.currentUserSubject.next(null);

    this.router.navigate(['/inicio']); // 👉 Ya no se manda a /login forzadamente
  }

  // -------------------------------
  // 👤 Obtener usuario actual
  // -------------------------------
  getCurrentUser(): User | null {
    return this.currentUserSubject.value;
  }

  // -------------------------------
  // 🔒 Verificar si está autenticado
  // -------------------------------
  isAuthenticated(): boolean {
    return this.isAuthenticatedSubject.value;
  }

  // -------------------------------
  // 🎭 Obtener rol actual
  // -------------------------------
  getUserRole(): 'CLIENT' | 'OWNER' | 'ADMIN' | null {
    const user = this.getCurrentUser();
    return user ? user.role : null;
  }

  // 🧠 Decodificar token JWT en usuario
  private buildUserFromToken(token: string): User | null {
    try {
      const payloadBase64 = token.split('.')[1];
      const payloadJson = atob(payloadBase64.replace(/-/g, '+').replace(/_/g, '/'));
      const payload = JSON.parse(payloadJson);

      return {
        id: payload.usuarioId,
        nombre: payload.nombre || payload.email,
        email: payload.email,
        role: payload.role?.toUpperCase()
      };
    } catch (error) {
      console.error('❌ Error decodificando token JWT:', error);
      return null;
    }
  }

  // -------------------------------
  // 🔄 Actualizar usuario
  // -------------------------------
  updateUser(user: User) {
    localStorage.setItem('user', JSON.stringify(user));
    this.currentUserSubject.next(user);
  }
}
