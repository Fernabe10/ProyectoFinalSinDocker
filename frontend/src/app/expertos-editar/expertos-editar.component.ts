import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ExpertoService } from '../experto.service';
import { ActivatedRoute, Router } from '@angular/router';
import { EspecialidadesService } from '../especialidades.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-expertos-editar',
  imports: [FormsModule, CommonModule],
  templateUrl: './expertos-editar.component.html',
  styleUrl: './expertos-editar.component.css'
})
export class ExpertosEditarComponent implements OnInit{
  
  especialidades: any[] = [];
  experto: any = { idUser: null, nombre: '', apellidos: '', dni: '' };
  idUser: number = 0;

  constructor(
    private route: ActivatedRoute,
    private servicio: ExpertoService,
    private router: Router,
    private servicioEspecialidades:EspecialidadesService
  ){}

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      this.idUser = +params.get('idUser')!;
      this.traerEspecialidad();
      this.traerEspecialidades();
    });
  }

  traerEspecialidad(): void {
    this.servicio.obtenerExpertoPorId(this.idUser).subscribe(
      (data) => {
        this.experto = data;
        console.log(this.experto);
      },
      (error) => {
        console.error('Error al obtener el experto', error);
      }
    );
  }

  traerEspecialidades(): void{
    this.servicioEspecialidades.traerEspecialidades().subscribe(
      (data) => {
        this.especialidades = data;
        console.log(this.especialidades);
      },
      (error) => {
        console.error('Error al obtener las especialidades', error);
      }
    );
  }

  editarExperto(): void {
    this.servicio.actualizarExperto(this.idUser, this.experto).subscribe(
      (response) => {
        console.log('Especialidad actualizada con éxito', response);
        this.router.navigate(['admin/experto']); 
      },
      (error) => {
        console.error('Error al actualizar el experto', error);
      }
    );
  }

}
