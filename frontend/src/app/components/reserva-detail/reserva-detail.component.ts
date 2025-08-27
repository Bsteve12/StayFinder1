import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { Reserva } from '../../models/reserva';
import { ReservaService } from '../../services/reserva.service';
import { catchError, of } from 'rxjs';

@Component({
  selector: 'app-reserva-detail',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './reserva-detail.component.html',
  styleUrls: ['./reserva-detail.component.css']
})
export class ReservaDetailComponent implements OnInit {
  reserva?: Reserva;
  loading = true;
  notFound = false;

  constructor(
    private route: ActivatedRoute,
    private reservaService: ReservaService
  ) {}

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');
    const id = Number(idParam);

    if (Number.isNaN(id)) {
      this.notFound = true;
      this.loading = false;
      return;
    }

    this.reservaService.getReservaById(id)
      .pipe(
        catchError(err => {
          console.error('Error al cargar reserva:', err);
          this.notFound = true;
          this.loading = false;
          return of(undefined);
        })
      )
      .subscribe((r: Reserva | undefined) => {
        this.reserva = r;
        this.notFound = !r;
        this.loading = false;
      });
  }
}
