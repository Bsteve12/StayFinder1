import { LoginComponent } from '../login/login.component';
import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { RegisterComponent } from "../register/register.component";

@Component({
  selector: 'app-group-auth',
  standalone: true,
  imports: [CommonModule, RegisterComponent, LoginComponent],
  templateUrl: './group-auth.component.html',
  styleUrl: './group-auth.component.css'
})
export class GroupAuthComponent {

  registerVisible = false;

  showRegisterForm() {
    this.registerVisible = !this.registerVisible;
    console.log(this.registerVisible)
    return this.registerVisible;
  }

}
