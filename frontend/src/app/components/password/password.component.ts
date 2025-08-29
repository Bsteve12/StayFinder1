import { Component, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-password',
  templateUrl: './password.component.html',
  styleUrls: ['./password.component.css']
})
export class PasswordComponent {
  email = '';
  @Output() back = new EventEmitter<void>();

  onRecover() {
    // Aquí va la lógica de recuperación
    alert('Recuperar para: ' + this.email);
  }
}