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
  role?: 'CLIENT' | 'OWNER' | 'ADMIN'; // 👈 Solo roles válidos
  foto?: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8000/api/usuario';
  private isAuthenticatedSubject = new BehaviorSubject<boolean>(false);
  private currentUserSubject = new BehaviorSubject<User | null>(null);

  public isAuthenticated$: Observable<boolean> = this.isAuthenticatedSubject.asObservable();
  public currentUser$: Observable<User | null> = this.currentUserSubject.asObservable();

  constructor(private http: HttpClient, private router: Router) {
    this.checkInitialAuth();
  }

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
    this.isAuthenticatedSubject.next(false);
    this.currentUserSubject.next(null);
    localStorage.removeItem('token');
  }

  login(credentials: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.apiUrl}/login`, credentials).pipe(
      tap((response) => {
        const token = response.token;
        localStorage.setItem('token', token);

        const user = this.buildUserFromToken(token);
        if (user) {
          localStorage.setItem('user', JSON.stringify(user));
          localStorage.setItem('role', user.role || '');
          this.isAuthenticatedSubject.next(true);
          this.currentUserSubject.next(user);
        } else {
          this.logout();
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

  private buildUserFromToken(token: string): User | null {
    try {
      const payloadBase64 = token.split('.')[1];
      const payloadJson = atob(payloadBase64.replace(/-/g, '+').replace(/_/g, '/'));
      const payload = JSON.parse(payloadJson);

      // Validar role estrictamente
      const role = payload.role?.toUpperCase();
      if (!['CLIENT', 'OWNER', 'ADMIN'].includes(role)) {
        console.warn('⚠ Rol inválido en token:', role);
        return null;
      }

      return {
        id: payload.usuarioId,
        email: payload.email || payload.sub,
        nombre: payload.nombre || payload.email,
        role: role as 'CLIENT' | 'OWNER' | 'ADMIN'
      };
    } catch (error) {
      console.error('❌ Error decodificando token JWT:', error);
      return null;
    }
  }
}
