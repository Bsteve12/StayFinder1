import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { InputTextModule } from 'primeng/inputtext';
import { PasswordModule } from 'primeng/password';
import { ButtonModule } from 'primeng/button';

@Component({
  selector: 'app-login',
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    InputTextModule,
    PasswordModule,
    ButtonModule
  ],
  templateUrl: './login.html',
  styleUrl: './login.scss',
})
export class Login {
  loginForm: FormGroup;
  showPassword: boolean = false;

  constructor(
    private fb: FormBuilder,
    private router: Router
  ) {
    this.loginForm = this.fb.group({
      username: ['', [Validators.required]],
      password: ['', [Validators.required, Validators.minLength(6)]]
    });
  }

  togglePasswordVisibility() {
    this.showPassword = !this.showPassword;
  }

  onLogin() {
    if (this.loginForm.valid) {
      console.log('Login:', this.loginForm.value);
      // Aquí iría la lógica de autenticación
      // this.authService.login(this.loginForm.value).subscribe(...)
    } else {
      // Marcar todos los campos como tocados para mostrar errores
      Object.keys(this.loginForm.controls).forEach(key => {
        this.loginForm.get(key)?.markAsTouched();
      });
    }
  }

  goToRegister() {
    console.log('Ir a registro');
    // this.router.navigate(['/register']);
  }

  goToForgotPassword() {
    console.log('Ir a recuperar contraseña');
    // this.router.navigate(['/forgot-password']);
  }

  // Helpers para validación
  get username() {
    return this.loginForm.get('username');
  }

  get password() {
    return this.loginForm.get('password');
  }

  get isUsernameInvalid() {
    return this.username?.invalid && this.username?.touched;
  }

  get isPasswordInvalid() {
    return this.password?.invalid && this.password?.touched;
  }
}
