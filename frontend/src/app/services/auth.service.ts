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
  user: User; // El backend debe enviarlo en este formato
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
    const userStr = localStorage.getItem('user');

    if (token && userStr) {
      try {
        const user: User = JSON.parse(userStr);
        this.isAuthenticatedSubject.next(true);
        this.currentUserSubject.next(user);
      } catch (error) {
        console.error('❌ Error al parsear usuario:', error);
        this.logout(false); // No redirigir al login aquí
      }
    }
  }

  // -------------------------------
  // 🔑 Login con API real
  // -------------------------------
  login(credentials: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.apiUrl}/login`, credentials).pipe(
      tap((response) => {
        // Guardar token y usuario
        localStorage.setItem('token', response.token);
        localStorage.setItem('user', JSON.stringify(response.user));
        localStorage.setItem('role', response.user.role);

        this.isAuthenticatedSubject.next(true);
        this.currentUserSubject.next(response.user);
      })
    );
  }

  // -------------------------------
  // 🚪 Logout
  // -------------------------------
  logout(redirect: boolean = true) {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    localStorage.removeItem('role');

    this.isAuthenticatedSubject.next(false);
    this.currentUserSubject.next(null);

    if (redirect) {
      this.router.navigate(['/inicio']); // ⬅️ redirigir a Inicio DE FORMA CORRECTA
    }
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

  // -------------------------------
  // 🔄 Actualizar usuario en perfil
  // -------------------------------
  updateUser(user: User) {
    localStorage.setItem('user', JSON.stringify(user));
    this.currentUserSubject.next(user);
  }
}
