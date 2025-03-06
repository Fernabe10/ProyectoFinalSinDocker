import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ParticipanteService {
  private apiUrl = 'http://localhost:9000/api/participante'

  constructor(private http:HttpClient) { }

  private obtenerHeaders(): HttpHeaders {
    const loginData = sessionStorage.getItem('LOGIN');
    const token = loginData ? JSON.parse(loginData).token : null; 
  
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
  }

  traerParticipantes(): Observable<any> {
    return this.http.get<any[]>(this.apiUrl); 
  }

  traerParticipantesPorEspecialidad(especialidad: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/especialidad/${especialidad}`);
  }  

  crearParticipante(participante: any): Observable<any> {
    return this.http.post<any>(this.apiUrl, participante, { headers: this.obtenerHeaders() });
  }

  obtenerParticipantePorId(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`, { headers: this.obtenerHeaders() });
  }

  actualizarParticipante(id: number, participante: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${id}`, participante, { headers: this.obtenerHeaders() });
  }

  borrarParticipantePorId(id: number): Observable<any>{
    return this.http.delete<any>(`${this.apiUrl}/${id}`, { headers: this.obtenerHeaders() });
  }
}
