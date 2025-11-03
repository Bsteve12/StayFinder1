import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatFormFieldModule } from '@angular/material/form-field';

import { ButtonComponent } from './atoms/button/button';
import { InputComponent } from './atoms/input/input';
import { LabelComponent } from './atoms/label/label';
import { FormFieldComponent } from './molecules/form-field/form-field';
import { LoginFormComponent } from './organisms/login-form/login-form';
import { AuthTemplateComponent } from './templates/auth-template/auth-template';
import { DesignShowcaseComponent } from './pages/design-showcase/design-showcase';
import { IconComponent} from './atoms/icon/icon';

// Agregar solo para probar el sistema de diseño
import { RouterModule, Routes } from '@angular/router';
const atomicRoutes: Routes = [
  {
    // Segmento restante: 'pages/design-showcase'
    path: 'pages/design-showcase',
    component: DesignShowcaseComponent
  }
];

@NgModule({
  declarations: [
    ButtonComponent,
    InputComponent,
    LabelComponent,
    FormFieldComponent,
    LoginFormComponent,
    AuthTemplateComponent,
    DesignShowcaseComponent,
    IconComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatButtonModule,
    MatInputModule,
    MatIconModule,
    MatFormFieldModule
    , RouterModule.forChild(atomicRoutes),// Agregar solo para probar el sistema de diseño
  ],
  exports: [
    ButtonComponent,
    InputComponent,
    LabelComponent,
    FormFieldComponent,
    LoginFormComponent,
    AuthTemplateComponent,
    DesignShowcaseComponent,
    IconComponent
  ]
})
export class AtomicModule {}
