import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { ParticipanteService } from '../participante.service';

@Component({
  selector: 'app-experto-participantes-editar',
  imports: [FormsModule],
  templateUrl: './experto-participantes-editar.component.html',
  styleUrl: './experto-participantes-editar.component.css'
})
export class ExpertoParticipantesEditarComponent implements OnInit{
  participante: any = { idParticipante: null, nombre: '', apellidos: '', dni: '' }
  idParticipante: number = 0;

  constructor(
    private route: ActivatedRoute,
    private servicio: ParticipanteService,
    private router: Router
  ) {}

  ngOnInit(): void {
   
    this.route.paramMap.subscribe(params => {
      this.idParticipante = +params.get('idParticipante')!;
      this.traerParticipante();
    });
  }

  traerParticipante(): void {
    this.servicio.obtenerParticipantePorId(this.idParticipante).subscribe(
      (data) => {
        this.participante = data;
      },
      (error) => {
        console.error('Error al obtener el participante', error);
      }
    );
  }

  editarParticipante(): void {
    this.servicio.actualizarParticipante(this.idParticipante, this.participante).subscribe(
      (response) => {
        console.log('Participante actualizado con éxito', response);
        this.router.navigate(['experto/participantes']); 
      },
      (error) => {
        console.error('Error al actualizar el participante', error);
      }
    );
  }


}
