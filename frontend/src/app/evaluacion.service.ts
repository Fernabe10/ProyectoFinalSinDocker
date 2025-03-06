import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EvaluacionService {

  private apiURL = 'http://localhost:9000/api/evaluacion';

  constructor(private http:HttpClient) { }

  private obtenerHeaders(): HttpHeaders {
    const loginData = sessionStorage.getItem('LOGIN');
    const token = loginData ? JSON.parse(loginData).token : null; 
  
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
  }

  traerEvaluaciones(): Observable<any> {
    return this.http.get<any[]>(this.apiURL, { headers: this.obtenerHeaders() });
  }

  crearEvaluacion(evaluacion: any): Observable<any> {
    return this.http.post<any>(this.apiURL, evaluacion, { headers: this.obtenerHeaders() });
  }

  traerLaUltimaId(): Observable<any> {
    return this.http.get<any>(`${this.apiURL}/ultimo`, { headers: this.obtenerHeaders() });
  }

  actualizarEvaluacionPorId(id: number, evaluacion: any): Observable<any> {
    return this.http.put<any>(`${this.apiURL}/${id}`, evaluacion, { headers: this.obtenerHeaders() });
  }

  buscarEvaluacionPorId(id: number):Observable<any> {
    return this.http.get<any>(`${this.apiURL}/${id}`, { headers: this.obtenerHeaders() });
  }

  
}
