import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { ParticipanteService } from '../participante.service';
import { OnInit } from '@angular/core';

@Component({
  selector: 'app-participantes',
  imports: [CommonModule],
  templateUrl: './participantes.component.html',
  styleUrl: './participantes.component.css'
})
export class ParticipantesComponent implements OnInit{
 ListaParticipantes:any[] = []; 

 constructor(private servicio:ParticipanteService){

 }

  ngOnInit(): void {
    this.traerCompetidores();
  }

  traerCompetidores():void {
    this.servicio.traerParticipantes().subscribe(
      (data) => {
        this.ListaParticipantes = data;
      },
      (error) => {
        console.error('Error al obtener los competidores', error);
      }
    )
  }


}
