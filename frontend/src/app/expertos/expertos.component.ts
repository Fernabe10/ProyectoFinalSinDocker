import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { ExpertoService } from '../experto.service';
import { EspecialidadesService } from '../especialidades.service';

@Component({
  selector: 'app-expertos',
  imports: [CommonModule, RouterLink],
  templateUrl: './expertos.component.html',
  styleUrl: './expertos.component.css'
})
export class ExpertosComponent implements OnInit {
  listaExpertos: any[] = [];
  listaEspecialidades: any[] = [];

  constructor(
    private servicio: ExpertoService,
    private servicioEspecialidades: EspecialidadesService
  ) {}

  ngOnInit(): void {
    this.traerExpertos();
    this.traerEspecialidades();
  }

  traerExpertos(): void {
    this.servicio.traerExpertos().subscribe(
      (data) => {
        this.listaExpertos = data;
      },
      (error) => {
        console.error('Error al obtener los expertos', error);
      }
    );
  }

  traerEspecialidades(): void {
    this.servicioEspecialidades.traerEspecialidades().subscribe(
      (datos) => {
        this.listaEspecialidades = datos;
      },
      (error) => {
        console.error('Error al obtener las especialidades', error);
      }
    );
  }

  obtenerNombreEspecialidad(id: number): string {
    const especialidad = this.listaEspecialidades.find(e => e.idEspecialidad === id);
    return especialidad ? especialidad.nombre : 'Sin especialidad';
  }

  borrarExperto(id: number): void {
    if (confirm('¿Estás seguro de que deseas eliminar este experto?')) {
      this.servicio.borrarExpertoPorId(id).subscribe(
        () => {
          alert('Experto eliminado correctamente.');
          this.traerExpertos(); // Refrescar la lista tras eliminar
        },
        (error) => {
          console.error('Error al eliminar experto', error);
          alert('Ocurrió un error al eliminar el experto.');
        }
      );
    }
  }
}
