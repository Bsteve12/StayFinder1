import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { ReservaListComponent } from './components/reserva-list/reserva-list.component';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, ReservaListComponent],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('Brandon');
}
