import { Component, OnInit } from '@angular/core';
import { EspecialidadesService } from '../especialidades.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-experto-especialidades',
  imports: [CommonModule],
  templateUrl: './experto-especialidades.component.html',
  styleUrl: './experto-especialidades.component.css'
})
export class ExpertoEspecialidadesComponent implements OnInit{

  listaEspecialidades: any[] = [];

  constructor(private servicio: EspecialidadesService){}


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
}
