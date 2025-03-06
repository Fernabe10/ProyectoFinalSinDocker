import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { EspecialidadesService } from '../especialidades.service';
import { UsuariosService } from '../usuario.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-expertos-crear',
  imports: [FormsModule, CommonModule],
  templateUrl: './expertos-crear.component.html',
  styleUrl: './expertos-crear.component.css'
})
export class ExpertosCrearComponent implements OnInit{

  usuario = {
    username: '',
    password: '',
    role: 'EXPERTO',
    nombre: '',
    dni: '',
    apellidos: '',
    especialidadId: ''
  };
  especialidades: any[] = [];

  constructor(
    private especialidadesService: EspecialidadesService,
    private UsuariosService:UsuariosService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.cargarEspecialidades();
  }

  cargarEspecialidades(): void {
    this.especialidadesService.traerEspecialidades().subscribe(
      (data) => {
        this.especialidades = data;
      },
      (error) => {
        console.error('Error al cargar las especialidades', error);
      }
    );
  }

  
  crearUsuario(): void {
    this.UsuariosService.crearExperto(this.usuario).subscribe(
      (response) => {
        console.log('Usuario creado con éxito:', response);
        this.router.navigate(['/admin/experto']);
      },
      (error) => {
        console.error('Error al crear el usuario', error);
      }
    );
  }
}
