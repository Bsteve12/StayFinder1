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
  // Tu backend devuelve solo token; aquí decodificamos para obtener user
}

export interface User {
  id?: number;              // optional porque token puede traer usuarioId
  nombre?: string;          // puede que no venga en token -> usar email
  email?: string;
  role?: 'CLIENT' | 'OWNER' | 'ADMIN' | string;
  foto?: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  // Ajusta a tu backend local (me dijiste que corre en 8000)
  private apiUrl = 'http://localhost:8000/api/usuario';

  private isAuthenticatedSubject = new BehaviorSubject<boolean>(false);
  private currentUserSubject = new BehaviorSubject<User | null>(null);

  public isAuthenticated$: Observable<boolean> = this.isAuthenticatedSubject.asObservable();
  public currentUser$: Observable<User | null> = this.currentUserSubject.asObservable();

  constructor(private http: HttpClient, private router: Router) {
    this.checkInitialAuth();
  }

  // --- init on reload
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
  }

  // --- login: POST /api/usuario/login -> { token }
  login(credentials: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.apiUrl}/login`, credentials).pipe(
      tap((response) => {
        const token = response.token;
        // guarda token
        localStorage.setItem('token', token);

        // decodifica token y crea user
        const user = this.buildUserFromToken(token);
        if (user) {
          localStorage.setItem('user', JSON.stringify(user));
          localStorage.setItem('role', (user.role || '') as string);
          this.isAuthenticatedSubject.next(true);
          this.currentUserSubject.next(user);
        } else {
          // Si no puede decodificar, limpia (no debería pasar)
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
    return user?.role ? (user.role as any) : null;
  }

  updateUser(user: User) {
    localStorage.setItem('user', JSON.stringify(user));
    this.currentUserSubject.next(user);
  }

  // -------- helpers --------
  private buildUserFromToken(token: string): User | null {
    try {
      // JWT = header.payload.signature
      const payloadBase64 = token.split('.')[1];
      const payloadJson = atob(payloadBase64.replace(/-/g, '+').replace(/_/g, '/'));
      const payload = JSON.parse(payloadJson);

      // Aquí asumimos que el backend incluye claims: usuarioId, email, role (según tu backend)
      const user: User = {
        id: payload.usuarioId ?? undefined,
        email: payload.email ?? payload.sub ?? undefined,
        nombre: payload.nombre ?? payload.email ?? undefined,
        role: payload.role ?? payload.authorities ?? undefined
      };

      return user;
    } catch (error) {
      console.error('Error decodificando token JWT:', error);
      return null;
    }
  }
}
