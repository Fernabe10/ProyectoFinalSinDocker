import { Component } from '@angular/core';
import { EspecialidadesService } from '../especialidades.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-especialidades-crear',
  imports: [CommonModule, FormsModule],
  templateUrl: './especialidades-crear.component.html',
  styleUrl: './especialidades-crear.component.css'
})
export class EspecialidadesCrearComponent {
  especialidad = { nombre: '', codigo: '' };

  constructor(private servicio: EspecialidadesService, private router: Router) {}

  crearEspecialidad(): void {
    this.servicio.crearEspecialidad(this.especialidad).subscribe(
      (response) => {
        console.log('Especialidad creada con éxito', response);
        this.router.navigate(['admin/especialidades']);
      },
      (error) => {
        console.error('Error al crear la especialidad', error);
      }
    );
  }
}
