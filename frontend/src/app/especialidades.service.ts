import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EspecialidadesService {
  private apiURL = 'http://localhost:9000/api/especialidad';

  constructor(private http: HttpClient) {}

  private obtenerHeaders(): HttpHeaders {
    const loginData = sessionStorage.getItem('LOGIN');
    const token = loginData ? JSON.parse(loginData).token : null; 
  
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
  }

  traerEspecialidades(): Observable<any> {
    return this.http.get<any[]>(this.apiURL, { headers: this.obtenerHeaders() });
  }

  crearEspecialidad(especialidad: any): Observable<any> {
    return this.http.post<any>(this.apiURL, especialidad, { headers: this.obtenerHeaders() });
  }

  obtenerEspecialidadPorId(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiURL}/${id}`, { headers: this.obtenerHeaders() });
  }

  actualizarEspecialidad(id: number, especialidad: any): Observable<any> {
    return this.http.put<any>(`${this.apiURL}/${id}`, especialidad, { headers: this.obtenerHeaders() });
  }

  borrarEspecialidadPorId(id: number): Observable<any>{
    return this.http.delete<any>(`${this.apiURL}/${id}`, { headers: this.obtenerHeaders() });
  }
}
