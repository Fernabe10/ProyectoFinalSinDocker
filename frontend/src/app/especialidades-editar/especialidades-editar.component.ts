import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { EspecialidadesService } from '../especialidades.service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-especialidades-editar',
  imports: [FormsModule],
  templateUrl: './especialidades-editar.component.html',
  styleUrl: './especialidades-editar.component.css'
})
export class EspecialidadEditarComponent implements OnInit{
  especialidad: any = { idEspecialidad: null, nombre: '', codigo: '' };
  idEspecialidad: number = 0;

  constructor(
    private route: ActivatedRoute,
    private servicio: EspecialidadesService,
    private router: Router
  ) {}

  ngOnInit(): void {
   
    this.route.paramMap.subscribe(params => {
      this.idEspecialidad = +params.get('idEspecialidad')!;
      this.traerEspecialidad();
    });
  }

  
  traerEspecialidad(): void {
    this.servicio.obtenerEspecialidadPorId(this.idEspecialidad).subscribe(
      (data) => {
        this.especialidad = data;
      },
      (error) => {
        console.error('Error al obtener la especialidad', error);
      }
    );
  }

  editarEspecialidad(): void {
    this.servicio.actualizarEspecialidad(this.idEspecialidad, this.especialidad).subscribe(
      (response) => {
        console.log('Especialidad actualizada con éxito', response);
        this.router.navigate(['admin/especialidades']); 
      },
      (error) => {
        console.error('Error al actualizar la especialidad', error);
      }
    );
  }
}
