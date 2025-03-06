import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { PruebasService } from '../pruebas.service';
import { ParticipanteService } from '../participante.service';
import { EspecialidadesService } from '../especialidades.service';
import { LoginService } from '../login.service';

@Component({
  selector: 'app-pruebas',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './pruebas.component.html',
  styleUrl: './pruebas.component.css'
})
export class PruebasComponent implements OnInit {
  listaPruebas: any[] = [];
  participantes: any[] = [];
  especialidades: any[] = [];
  pruebaSeleccionada: any | null = null;
  especialidadUsuario: number = 0; // Variable para almacenar la especialidad del usuario

  constructor(
    private servicio: PruebasService,
    private servicioParticipantes: ParticipanteService,
    private servicioEspecialidades: EspecialidadesService,
    private loginService: LoginService // Inyectar el servicio de login
  ) {}

  ngOnInit(): void {
    this.especialidadUsuario = this.loginService.getEspecialidad(); // Obtener la especialidad del usuario logueado
    this.traerPruebas();
    this.traerEspecialidades();
  }

  traerPruebas(): void {
    this.servicio.traerPruebas().subscribe(
      (data) => {
        // Filtrar pruebas por la especialidad del usuario
        this.listaPruebas = data.filter((prueba: { especialidad: { idEspecialidad: number; }; }) => prueba.especialidad.idEspecialidad === this.especialidadUsuario);
        console.log(this.listaPruebas);
      },
      (error) => {
        console.error('Error al obtener las pruebas', error);
      }
    );
  }

  traerParticipantesPorEspecialidad(especialidadId: number): void {
    this.servicioParticipantes.traerParticipantesPorEspecialidad(especialidadId).subscribe(
      (data) => {
        this.participantes = data;
        console.log(this.participantes);
      },
      (error) => {
        console.error('Error al obtener los participantes por especialidad', error);
      }
    );
  }

  traerEspecialidades(): void {
    this.servicioEspecialidades.traerEspecialidades().subscribe(
      (datos) => {
        this.especialidades = datos;
        console.log(this.especialidades);
      },
      (error) => {
        console.error('Error al obtener las especialidades', error);
      }
    );
  }

  borrarPrueba(id: number): void {
    if (confirm('¿Estás seguro de que quieres borrar esta prueba?')) {
      this.servicio.borrarPruebaPorId(id).subscribe(
        () => {
          this.listaPruebas = this.listaPruebas.filter(prueba => prueba.idPrueba !== id);
        },
        (error) => {
          console.error('Error al borrar la prueba', error);
        }
      );
    }
  }
  

  // Función que se llama cuando se hace clic en "Lista de participantes"
  mostrarParticipantes(idPrueba: number): void {
    this.pruebaSeleccionada = this.listaPruebas.find(prueba => prueba.idPrueba === idPrueba) || null;
    
    if (this.pruebaSeleccionada) {
      const especialidadId = this.pruebaSeleccionada.especialidad.idEspecialidad; 
      this.traerParticipantesPorEspecialidad(especialidadId);
    }
  }
}
