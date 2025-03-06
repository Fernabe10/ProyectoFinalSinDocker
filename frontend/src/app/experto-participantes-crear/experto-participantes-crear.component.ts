import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ParticipanteService } from '../participante.service';
import { Router } from '@angular/router';
import { EspecialidadesService } from '../especialidades.service';

@Component({
  selector: 'app-experto-participantes-crear',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './experto-participantes-crear.component.html',
  styleUrl: './experto-participantes-crear.component.css'
})
export class ExpertoParticipantesCrearComponent implements OnInit {
  participante = { 
    nombre: '', 
    apellidos: '', 
    centro: '', 
    especialidad: { idEspecialidad: 0 } 
  };

  especialidad = {
    idEspecialidad: '',
    codigo: '',
    nombre: ''
  }

  constructor(
    private servicio: ParticipanteService,
    private router: Router,
    private especialidadService: EspecialidadesService) {}

    ngOnInit(): void {
      
      const loginData = sessionStorage.getItem('LOGIN');
      if (loginData) {
        const usuarioLogueado = JSON.parse(loginData);
        this.participante.especialidad.idEspecialidad = usuarioLogueado.especialidadId;
    
        
        this.especialidadService.obtenerEspecialidadPorId(usuarioLogueado.especialidadId).subscribe(
          (especialidad) => {
            this.especialidad = especialidad;
          },
          (error) => {
            console.error('Error al obtener la especialidad', error);
          }
        );
      }
    }
    

  crearParticipante(): void {
    this.servicio.crearParticipante(this.participante).subscribe(
      (response) => {
        console.log('Participante creado con éxito', response);
        this.router.navigate(['/experto/participantes']);
      },
      (error) => {
        console.error('Error al crear el participante', error);
      }
    );
  }
}
