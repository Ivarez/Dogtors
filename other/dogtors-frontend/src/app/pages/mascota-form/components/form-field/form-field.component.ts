import { Component, input } from '@angular/core';
import { FormControl, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-form-field',
  imports: [ReactiveFormsModule],
  templateUrl: './form-field.component.html',
  styleUrl: './form-field.component.scss'
})
export class FormFieldComponent {
  label = input.required<string>();
  control = input.required<FormControl>();
  fieldId = input.required<string>();
  type = input<string>('text');
  placeholder = input<string>('');
  errorMessage = input<string>('Este campo es obligatorio.');
  hint = input<string>('');
  isFullWidth = input<boolean>(false);
}
