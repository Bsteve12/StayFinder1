import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-label',
  standalone: false,
  templateUrl: './label.html',
  styleUrl: './label.scss',
})
export class Label {
  @Input() text = '';
  @Input() forId = '';
  @Input() color = 'text-gray-800';
}
