import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule, FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import { Router } from '@angular/router';
import { InputTextModule } from 'primeng/inputtext';
import { ButtonModule } from 'primeng/button';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatDialog, MatDialogModule, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { provideNativeDateAdapter, MAT_DATE_FORMATS, MAT_NATIVE_DATE_FORMATS } from '@angular/material/core';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-register',
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    InputTextModule,
    ButtonModule,
    MatButtonModule
  ],
  providers: [provideNativeDateAdapter()],
  templateUrl: './register.html',
  styleUrl: './register.scss',
})
export class Register {
  registerForm: FormGroup;
  dialog = inject(MatDialog);

  constructor(
    private fb: FormBuilder,
    private router: Router
  ) {
    this.registerForm = this.fb.group({
      username: ['', [Validators.required, Validators.minLength(3)]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      phone: ['', [Validators.required, Validators.pattern(/^[0-9]{10}$/)]],
      birthdate: [null, [Validators.required]]
    });
  }

  onRegister() {
    if (this.registerForm.valid) {
      console.log('Registro:', this.registerForm.value);
      // Aquí iría la lógica de registro
      // this.authService.register(this.registerForm.value).subscribe(...)
    } else {
      // Marcar todos los campos como tocados para mostrar errores
      Object.keys(this.registerForm.controls).forEach(key => {
        this.registerForm.get(key)?.markAsTouched();
      });
    }
  }

  goToLogin() {
    console.log('Ir a login');
    // this.router.navigate(['/login']);
  }

  openBirthdateDialog() {
    const dialogRef = this.dialog.open(BirthdateDatePickerDialog, {
      data: { selectedDate: this.birthdate?.value, title: 'Selecciona tu fecha de nacimiento' }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.registerForm.patchValue({ birthdate: result });
        this.birthdate?.markAsTouched();
      }
    });
  }

  formatDate(date: Date | null): string {
    if (!date) return 'Fecha de nacimiento';
    return new Date(date).toLocaleDateString('es-ES', { day: '2-digit', month: '2-digit', year: 'numeric' });
  }

  // Helpers para validación
  get username() {
    return this.registerForm.get('username');
  }

  get email() {
    return this.registerForm.get('email');
  }

  get password() {
    return this.registerForm.get('password');
  }

  get phone() {
    return this.registerForm.get('phone');
  }

  get birthdate() {
    return this.registerForm.get('birthdate');
  }

  get isUsernameInvalid() {
    return this.username?.invalid && this.username?.touched;
  }

  get isEmailInvalid() {
    return this.email?.invalid && this.email?.touched;
  }

  get isPasswordInvalid() {
    return this.password?.invalid && this.password?.touched;
  }

  get isPhoneInvalid() {
    return this.phone?.invalid && this.phone?.touched;
  }

  get isBirthdateInvalid() {
    return this.birthdate?.invalid && this.birthdate?.touched;
  }

  getUsernameError(): string {
    if (this.username?.hasError('required')) return 'El usuario es requerido';
    if (this.username?.hasError('minlength')) return 'Mínimo 3 caracteres';
    return '';
  }

  getEmailError(): string {
    if (this.email?.hasError('required')) return 'El email es requerido';
    if (this.email?.hasError('email')) return 'Email inválido';
    return '';
  }

  getPasswordError(): string {
    if (this.password?.hasError('required')) return 'La contraseña es requerida';
    if (this.password?.hasError('minlength')) return 'Mínimo 6 caracteres';
    return '';
  }

  getPhoneError(): string {
    if (this.phone?.hasError('required')) return 'El teléfono es requerido';
    if (this.phone?.hasError('pattern')) return 'Debe tener 10 dígitos';
    return '';
  }

  getBirthdateError(): string {
    if (this.birthdate?.hasError('required')) return 'La fecha es requerida';
    return '';
  }

  getMaxDate(): Date {
    const today = new Date();
    today.setFullYear(today.getFullYear() - 18); // Mínimo 18 años
    return today;
  }
}

// Dialog Component para el DatePicker de Fecha de Nacimiento
@Component({
  selector: 'birthdate-date-picker-dialog',
  standalone: true,
  imports: [
    CommonModule,
    MatDatepickerModule,
    MatDialogModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    ReactiveFormsModule,
  ],
  providers: [
    provideNativeDateAdapter(),
    { provide: MAT_DATE_FORMATS, useValue: MAT_NATIVE_DATE_FORMATS },
  ],
  template: `
    <div class="dialog-container">
      <h2 mat-dialog-title>{{ data.title }}</h2>
      <mat-dialog-content>
        <mat-form-field>
          <mat-label>Selecciona una fecha</mat-label>
          <input matInput [matDatepicker]="picker" [formControl]="date" [max]="maxDate">
          <mat-datepicker-toggle matIconSuffix [for]="picker"></mat-datepicker-toggle>
          <mat-datepicker #picker></mat-datepicker>
        </mat-form-field>
      </mat-dialog-content>
      <mat-dialog-actions align="end">
        <button mat-button (click)="onCancel()">Cancelar</button>
        <button mat-raised-button color="primary" (click)="onConfirm()">Confirmar</button>
      </mat-dialog-actions>
    </div>
  `,
  styles: [`
    .dialog-container {
      padding: 20px;
      min-width: 300px;
    }
    
    mat-form-field {
      width: 100%;
      margin-top: 16px;
    }
    
    mat-dialog-actions {
      margin-top: 24px;
      padding-top: 16px;
    }
  `]
})
export class BirthdateDatePickerDialog {
  dialogRef = inject<MatDialogRef<BirthdateDatePickerDialog>>(MatDialogRef<BirthdateDatePickerDialog>);
  data = inject(MAT_DIALOG_DATA);
  date = new FormControl(new Date());
  maxDate = new Date();

  constructor() {
    // Establecer la fecha máxima (18 años atrás)
    this.maxDate.setFullYear(this.maxDate.getFullYear() - 18);
    
    if (this.data.selectedDate) {
      this.date.setValue(this.data.selectedDate);
    } else {
      // Fecha por defecto: hace 18 años
      this.date.setValue(this.maxDate);
    }
  }

  onCancel() {
    this.dialogRef.close();
  }

  onConfirm() {
    this.dialogRef.close(this.date.value);
  }
}
