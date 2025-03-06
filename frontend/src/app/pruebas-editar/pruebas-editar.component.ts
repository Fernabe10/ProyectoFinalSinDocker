import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { PruebasService } from '../pruebas.service';
import { EspecialidadesService } from '../especialidades.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-pruebas-editar',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './pruebas-editar.component.html',
  styleUrl: './pruebas-editar.component.css'
})
export class PruebasEditarComponent implements OnInit {
  prueba: any = { idPrueba: null, enunciado: '', especialidad: null };
  idPrueba: number = 0;
  especialidades: any[] = []; 
  idEspecialidad: number = 0; 
  archivo: File | null = null;

  constructor(
    private route: ActivatedRoute,
    private servicio: PruebasService,
    private router: Router,
    private servicioEspecialidad: EspecialidadesService
  ) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      this.idPrueba = +params.get('idPrueba')!;
      this.traerPrueba();
      this.traerEspecialidades();
    });
  }

  traerPrueba(): void {
    this.servicio.obtenerPruebaPorId(this.idPrueba).subscribe(
      (data) => {
        this.prueba = data;
        this.idEspecialidad = data.especialidad.idEspecialidad; 
      },
      (error) => {
        console.error('Error al obtener la prueba', error);
      }
    );
  }

  traerEspecialidades(): void {
    this.servicioEspecialidad.traerEspecialidades().subscribe(
      (data) => {
        this.especialidades = data; 
      },
      (error) => {
        console.error('Error al obtener las especialidades', error);
      }
    );
  }

  seleccionarArchivo(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.archivo = input.files[0];
    }
  }

  editarPrueba(): void {
    const datosPrueba = {
      idPrueba: this.idPrueba,
      puntuacionMaxima: this.prueba.puntuacionMaxima,
      especialidad: { idEspecialidad: this.idEspecialidad }
    };
  
    this.servicio.actualizarPrueba(this.idPrueba, datosPrueba, this.archivo ?? undefined).subscribe(
      (response) => {
        console.log('Prueba actualizada con éxito', response);
        this.router.navigate(['experto/pruebas']); 
      },
      (error) => {
        console.error('Error al actualizar la prueba', error);
      }
    );
  }
  
}
