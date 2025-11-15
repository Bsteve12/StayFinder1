import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { Router } from '@angular/router';

export interface LoginRequest {
  email: string;
  contrasena: string;
}

export interface LoginResponse {
  token: string; // Solo recibes el token desde el backend
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

  private checkInitialAuth() {
    const token = localStorage.getItem('token');
    if (token) {
      const user = this.buildUserFromToken(token);
      if (user) {
        this.isAuthenticatedSubject.next(true);
        this.currentUserSubject.next(user);
      } else {
        this.logout();
      }
    }
  }

  login(credentials: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.apiUrl}/login`, credentials).pipe(
      tap((response) => {
        const token = response.token;
        localStorage.setItem('token', token);

        const user = this.buildUserFromToken(token);
        if (user) {
          localStorage.setItem('user', JSON.stringify(user));
          localStorage.setItem('role', user.role);
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
    return this.currentUserSubject.value?.role ?? null;
  }

  updateUser(user: User) {
    localStorage.setItem('user', JSON.stringify(user));
    this.currentUserSubject.next(user);
  }

  // 🧠 Decodificamos el token para sacar el JSON del usuario
  private buildUserFromToken(token: string): User | null {
    try {
      const payloadBase64 = token.split('.')[1];
      const payloadJson = atob(payloadBase64.replace(/-/g, '+').replace(/_/g, '/'));
      const payload = JSON.parse(payloadJson);

      const role = payload.role?.toUpperCase();
      return {
        id: payload.usuarioId,
        email: payload.email || payload.sub,
        nombre: payload.nombre || payload.email,
        role: role as 'CLIENT' | 'OWNER' | 'ADMIN'
      };
    } catch (error) {
      console.error('❌ Error decodificando token:', error);
      return null;
    }
  }
}
