import { Component, Output, EventEmitter } from '@angular/core';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  username = '';
  password = '';
  @Output() forgot = new EventEmitter<void>();
  @Output() register = new EventEmitter<void>();

  onLogin() {
    // Aquí va la lógica de login
    alert('Login: ' + this.username);
  }
}