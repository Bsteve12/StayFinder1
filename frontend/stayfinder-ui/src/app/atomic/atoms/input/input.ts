import { Component, Input } from '@angular/core';
import { FormControl } from '@angular/forms';
@Component({
  selector: 'app-input',
  standalone: false,
  templateUrl: './input.html',
  styleUrl: './input.scss',
})
export class InputComponent {
  @Input() control!: FormControl;
  @Input() placeholder!: string;
  @Input() type: string = 'text';
  @Input() icon?: string;
}
