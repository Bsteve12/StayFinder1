import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { InputTextModule } from 'primeng/inputtext';
import { PasswordModule } from 'primeng/password';
import { ButtonModule } from 'primeng/button';
import { ToastModule } from 'primeng/toast'; // ✅ Solo necesitas este
import { AuthService, LoginResponse } from '../services/auth.service';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'app-login',
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    InputTextModule,
    PasswordModule,
    ButtonModule,
    ToastModule // ✅ módulo necesario para <p-toast>
  ],
  templateUrl: './login.html',
  styleUrls: ['./login.scss'],
  providers: [MessageService]
})
export class Login {
  loginForm: FormGroup;
  showPassword = false;
  loading = false;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private authService: AuthService,
    private messageService: MessageService
  ) {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required]],
      password: ['', [Validators.required, Validators.minLength(6)]]
    });
  }

  onLogin() {
    console.log('🟢 Intentando iniciar sesión...');

    if (this.loginForm.invalid) {
      Object.keys(this.loginForm.controls).forEach(key => {
        this.loginForm.get(key)?.markAsTouched();
      });
      return;
    }

    const credentials = {
      email: this.loginForm.value.email,
      contrasena: this.loginForm.value.password
    };

    this.loading = true;

    // Nota: backend devuelve { token }, AuthService ahora decodifica el token y publica user
    this.authService.login(credentials).subscribe({
      next: (response: LoginResponse) => {
        console.log('✅ Login exitoso. Token recibido');

        // Mensaje de éxito
        this.messageService.add({
          severity: 'success',
          summary: 'Inicio de sesión exitoso',
          detail: 'Bienvenido a StayFinder'
        });

        // Redirigir al inicio (tu comportamiento anterior)
        setTimeout(() => {
          this.router.navigate(['/inicio']);
        }, 1500);
      },
      error: (err: any) => {
        console.error('❌ Error en login:', err);
        this.messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: err?.error?.message ?? 'Credenciales inválidas o usuario no encontrado'
        });
      },
      complete: () => (this.loading = false)
    });
  }

  goToRegister() { this.router.navigate(['/register']); }
  goToForgotPassword() { this.router.navigate(['/forgot-password']); }
  togglePasswordVisibility() { this.showPassword = !this.showPassword; }

  // GETTERS para la plantilla (no tocados)
  get email() { return this.loginForm.get('email'); }
  get password() { return this.loginForm.get('password'); }
  get isEmailInvalid() { return this.email?.invalid && this.email?.touched; }
  get isPasswordInvalid() { return this.password?.invalid && this.password?.touched; }
}
