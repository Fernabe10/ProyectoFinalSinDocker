import { Component, OnInit } from '@angular/core';
import { ParticipanteService } from '../participante.service';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { LoginService } from '../login.service';

@Component({
  selector: 'app-experto-participantes',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './experto-participantes.component.html',
  styleUrl: './experto-participantes.component.css'
})
export class ExpertoParticipantesComponent implements OnInit {
  listaParticipantes: any[] = [];
  idEspecialidad: number = 0;

  constructor(
    private servicio: ParticipanteService,
    private servicioLogin: LoginService
  ) {}

  ngOnInit(): void {
    this.idEspecialidad = this.servicioLogin.getEspecialidad();
    this.traerCompetidoresPorEspecialidad();
  }

  traerCompetidoresPorEspecialidad(): void {
    this.servicio.traerParticipantes().subscribe(
      (data) => {
        this.listaParticipantes = data.filter(
          (participante: { especialidad: { idEspecialidad: number; }; }) => participante.especialidad.idEspecialidad === this.idEspecialidad
        );
      },
      (error) => {
        console.error('Error al obtener los competidores', error);
      }
    );
  }

  borrarParticipante(id: number): void {
    if (confirm('¿Estás seguro de que quieres borrar este participante?')) {
      this.servicio.borrarParticipantePorId(id).subscribe(
        () => {
          this.listaParticipantes = this.listaParticipantes.filter(participante => participante.idParticipante !== id);
        },
        (error) => {
          console.error('Error al borrar el participante', error);
        }
      );
    }
  }
}
