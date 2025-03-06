import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { EspecialidadesService } from '../especialidades.service';
import { EvaluacionService } from '../evaluacion.service';
import { ParticipanteService } from '../participante.service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-ganadores',
  imports: [CommonModule, FormsModule],
  templateUrl: './ganadores.component.html',
  styleUrl: './ganadores.component.css'
})
export class GanadoresComponent implements OnInit{
  listaEspecialidades:any[] = [];
  listaParticipantes: any[] = [];  // Inicializamos como un array vacío
  listaEvaluaciones: any[] = [];

  especialidadSeleccionada: number = 0;

  constructor(
    private especialidadesService: EspecialidadesService,
    private evaluacionService: EvaluacionService,
    private participatesService: ParticipanteService
  ){}

  ngOnInit(): void {
    this.traerEspecialidades();
    this.traerEvaluaciones();
  }

  traerParticipantesPorEspecialidad(especialidadId: number): void {
    // Asegurarse de que listaParticipantes sea siempre un array vacío
    this.listaParticipantes = [];
    
    this.participatesService.traerParticipantesPorEspecialidad(especialidadId).subscribe(
      (data) => {
        this.listaParticipantes = data;
        this.ordenarParticipantesPorNota();
        console.log(this.listaParticipantes);
      },
      (error) => {
        console.error('Error al obtener los participantes por especialidad', error);
        this.listaParticipantes = []; // Limpiar la lista si hay un error
      }
    );
  }

  obtenerNotaFinal(participanteId: number): number | string {
    const evaluacion = this.listaEvaluaciones.find(evaluacion => evaluacion.participanteId === participanteId);
    if (evaluacion) {
      return evaluacion.notaFinal;
    } else {
      return 'Sin nota';
    }
  }

  ordenarParticipantesPorNota(): void {
    this.listaParticipantes.sort((a, b) => {
      const notaA = this.obtenerNotaFinal(a.idParticipante);
      const notaB = this.obtenerNotaFinal(b.idParticipante);
      return typeof notaB === 'number' && typeof notaA === 'number' ? notaB - notaA : 0;
    });
  }

  // METODOS PARA TRAER TODOS LAS COSAS
  traerEspecialidades(): void {
    this.especialidadesService.traerEspecialidades().subscribe(
      (data) => {
        this.listaEspecialidades = data;
        console.log(this.listaEspecialidades);
      },
      (error) => {
        console.error('Error al obtener las especialidades', error);
      }
    );
  }

  traerEvaluaciones(): void {
    this.evaluacionService.traerEvaluaciones().subscribe(
      (data) => {
        this.listaEvaluaciones = data;
        console.log(this.listaEvaluaciones);
      },
      (error) => {
        console.error('Error al obtener las evaluaciones', error);
      }
    );
  }
}
