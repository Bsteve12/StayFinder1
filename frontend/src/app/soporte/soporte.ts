import { Component } from '@angular/core';
import { AccordionModule } from 'primeng/accordion';
import { ButtonModule } from 'primeng/button';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-soporte',
  imports: [
    CommonModule,
    AccordionModule,
    ButtonModule],
  templateUrl: './soporte.html',
  styleUrl: './soporte.scss',
})
export class Soporte {

}
