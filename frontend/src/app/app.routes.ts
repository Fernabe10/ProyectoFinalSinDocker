// src/app/app.routes.ts
import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { BienvenidaComponent } from './bienvenida/bienvenida.component';
import { ParticipantesComponent } from './participantes/participantes.component';
import { AdminComponent } from './admin/admin.component';
import { ExpertosComponent } from './expertos/expertos.component';
import { EspecialidadesComponent } from './especialidades/especialidades.component';
import { EspecialidadesCrearComponent } from './especialidades-crear/especialidades-crear.component';
import { EspecialidadEditarComponent } from './especialidades-editar/especialidades-editar.component';
import { adminGuard } from './admin.guard';
import { ExpertosEditarComponent } from './expertos-editar/expertos-editar.component';
import { ExpertosCrearComponent } from './expertos-crear/expertos-crear.component';
import { ExpertoComponent } from './experto/experto.component';
import { expertoGuard } from './experto.guard';
import { PruebasComponent } from './pruebas/pruebas.component';
import { ExpertoParticipantesComponent } from './experto-participantes/experto-participantes.component';
import { ExpertoParticipantesEditarComponent } from './experto-participantes-editar/experto-participantes-editar.component';
import { PruebasEditarComponent } from './pruebas-editar/pruebas-editar.component';
import { ExpertoParticipantesCrearComponent } from './experto-participantes-crear/experto-participantes-crear.component';
import { PruebasCrearComponent } from './pruebas-crear/pruebas-crear.component';
import { PruebasEvaluarComponent } from './pruebas-evaluar/pruebas-evaluar.component';
import { GanadoresComponent } from './ganadores/ganadores.component';
import { ExpertoEspecialidadesComponent } from './experto-especialidades/experto-especialidades.component';

export const appRoutes: Routes = [

  //Vistas publicas
  { path: '', component: BienvenidaComponent }, 
  { path: 'login', component: LoginComponent }, 
  { path: 'participantes', component: ParticipantesComponent }, 

  //Vistas de admin
  { path: 'admin', component: AdminComponent, canActivate: [adminGuard] },
  { path: 'admin/experto', component: ExpertosComponent, canActivate: [adminGuard]},
  { path: 'admin/especialidades', component: EspecialidadesComponent, canActivate: [adminGuard] },
  { path: 'admin/especialidades/crear', component: EspecialidadesCrearComponent, canActivate: [adminGuard] },
  { path: 'admin/experto/crear', component: ExpertosCrearComponent },
  { path: 'admin/especialidades/editar/:idEspecialidad', component: EspecialidadEditarComponent, canActivate: [adminGuard] },
  { path: 'admin/experto/editar/:idUser', component: ExpertosEditarComponent, canActivate: [adminGuard] },
  { path: 'admin/ganadores', component: GanadoresComponent, canActivate: [adminGuard]},


  //Vistas de experto
  { path: 'experto', component: ExpertoComponent, canActivate: [expertoGuard] },
  { path: 'experto/pruebas', component: PruebasComponent, canActivate: [expertoGuard] },
  { path: 'experto/pruebas/crear', component: PruebasCrearComponent, canActivate: [expertoGuard] },
  { path: 'experto/pruebas/evaluar/:idPrueba/:idParticipante', component: PruebasEvaluarComponent, canActivate: [expertoGuard] },
  { path: 'experto/pruebas/editar/:idPrueba', component: PruebasEditarComponent, canActivate: [expertoGuard] },
  { path: 'experto/participantes', component: ExpertoParticipantesComponent, canActivate: [expertoGuard] },
  { path: 'experto/participantes/crear', component: ExpertoParticipantesCrearComponent, canActivate: [expertoGuard] },
  { path: 'experto/participantes/editar/:idParticipante', component: ExpertoParticipantesEditarComponent, canActivate: [expertoGuard] },
  { path: 'experto/especialidades', component: ExpertoEspecialidadesComponent, canActivate: [expertoGuard] },
  
  
  { path: '', redirectTo: '/login', pathMatch: 'full' }
];
