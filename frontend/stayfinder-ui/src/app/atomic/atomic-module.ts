import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatFormFieldModule } from '@angular/material/form-field';

import { Button } from './atoms/button/button';
import { InputComponent } from './atoms/input/input';
import { Label } from './atoms/label/label';
import { Icon } from './atoms/icon/icon';
import { FormField } from './molecules/form-field/form-field';
import { LoginForm } from './organisms/login-form/login-form';
import { AuthTemplate } from './templates/auth-template/auth-template';



@NgModule({
  declarations: [
    Button,
    InputComponent,
    Label,
    Icon,
    FormField,
    LoginForm,
    AuthTemplate
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatButtonModule,
    MatInputModule,
    MatIconModule,
    MatFormFieldModule
  ],
  exports: [
    Button,
    InputComponent,
    Label,
    Icon,
    FormField,
    LoginForm,
    AuthTemplate
  ]
})
export class AtomicModule { }
