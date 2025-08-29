import { Component, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  username: string = '';
  email: string = '';
  password: string = '';

  @Output() login = new EventEmitter<void>();

  onRegister() {
    // Implementar lógica de registro aquí
    console.log('Register attempt:', {
      username: this.username,
      email: this.email,
      password: this.password
    });
  }
}