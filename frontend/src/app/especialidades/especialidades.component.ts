import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { EspecialidadesService } from '../especialidades.service';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-especialidades',
  imports: [CommonModule, RouterLink],
  templateUrl: './especialidades.component.html',
  styleUrl: './especialidades.component.css'
})
export class EspecialidadesComponent implements OnInit{
  listaEspecialidades: any[] = [];

  constructor(private servicio:EspecialidadesService){}

  ngOnInit(): void {
    this.traerEspecialidades();
  }

  traerEspecialidades():void {
    this.servicio.traerEspecialidades().subscribe(
      (data) => {
        this.listaEspecialidades = data;
      },
      (error) => {
        console.error('Error al obtener las especialidades', error);
      }
    )
  }

  borrarEspecialidad(id: number): void {
    if (confirm('¿Estás seguro de que quieres borrar esta especialidad?')) {
      this.servicio.borrarEspecialidadPorId(id).subscribe(
        () => {
          this.listaEspecialidades = this.listaEspecialidades.filter(e => e.idEspecialidad !== id);
        },
        (error) => {
          console.error('Error al borrar la especialidad', error);
        }
      );
    }
  }
  

}
