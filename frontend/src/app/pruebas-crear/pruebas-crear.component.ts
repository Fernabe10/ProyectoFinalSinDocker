import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { PruebasService } from '../pruebas.service';
import { LoginService } from '../login.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { EspecialidadesService } from '../especialidades.service';

@Component({
  selector: 'app-pruebas-crear',
  templateUrl: './pruebas-crear.component.html',
  styleUrls: ['./pruebas-crear.component.css'],
  imports: [FormsModule, CommonModule]
})
export class PruebasCrearComponent implements OnInit {
  prueba: any = { puntuacionMaxima: null };
  especialidad: any = { idEspecialidad: '', codigo: '', nombre: '' };
  archivo: File | null = null;
  pruebaCreada: any = null;
  item: any = { descripcion: '', peso: null, gradosConsecucion: null };
  itemsAgregados: any[] = [];

  constructor(
    private servicio: PruebasService,
    private servicioLogin: LoginService,
    private especialidadService: EspecialidadesService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.cargarEspecialidad();
  }

  // Método separado para cargar la especialidad
  cargarEspecialidad(): void {
    const idEspecialidad = this.servicioLogin.getEspecialidad();
    if (idEspecialidad) {
      this.especialidad.idEspecialidad = idEspecialidad;
      console.log('ID Especialidad:', idEspecialidad);

      // Obtener el nombre de la especialidad desde el backend
      this.especialidadService.obtenerEspecialidadPorId(idEspecialidad).subscribe(
        (especialidad) => {
          this.especialidad = especialidad;
          console.log('Especialidad cargada:', this.especialidad);
        },
        (error) => {
          console.error('Error al cargar la especialidad:', error);
        }
      );
    }
  }

  crearPrueba(): void {
    const formData = new FormData();
    formData.append('idEspecialidad', this.especialidad.idEspecialidad.toString()); // Ahora envía la ID
    formData.append('puntuacionMaxima', this.prueba.puntuacionMaxima.toString());

    if (this.archivo) {
      formData.append('file', this.archivo);
    }

    this.servicio.crearPrueba(formData).subscribe(
      (response) => {
        console.log('Prueba creada con éxito', response);
        this.pruebaCreada = response;
      },
      (error) => {
        console.error('Error al crear la prueba', error);
      }
    );
  }

  seleccionarArchivo(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.archivo = input.files[0];
    }
  }

  agregarItem(): void {
    if (!this.pruebaCreada) {
      console.error('No hay una prueba creada a la cual asignar ítems.');
      return;
    }

    const itemData = {
      descripcion: this.item.descripcion,
      peso: this.item.peso,
      gradosConsecucion: this.item.gradosConsecucion,
      idPrueba: this.pruebaCreada.idPrueba
    };

    this.servicio.agregarItem(itemData).subscribe(
      (response) => {
        console.log('Ítem agregado con éxito', response);
        this.itemsAgregados.push(response); 
        this.resetFormularioItem(); 
      },
      (error) => {
        console.error('Error al agregar el ítem', error);
      }
    );
  }

  mostrarFormularioItem(): void {
    this.resetFormularioItem();
  }

  resetFormularioItem(): void {
    this.item = { descripcion: '', peso: null, gradosConsecucion: null };
  }
}
