import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { ReservaListComponent } from './components/reserva-list/reserva-list.component';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, ReservaListComponent],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  protected readonly title = signal('Brandon');
}
