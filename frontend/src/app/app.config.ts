import { ApplicationConfig, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';
import { provideHttpClient } from '@angular/common/http'; // Importar provideHttpClient
import { appRoutes } from './app.routes'; // Rutas de la aplicación

export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }), // Detectar cambios de zona
    provideRouter(appRoutes), // Configurar el enrutador
    provideHttpClient(), // Proveer HttpClient para que se pueda usar en los servicios
  ]
};
