import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ExpertoService {

  private apiURL = 'http://localhost:9000/api/experto';

  constructor(private http:HttpClient) { }

  private obtenerHeaders(): HttpHeaders {
    const loginData = sessionStorage.getItem('LOGIN');
    const token = loginData ? JSON.parse(loginData).token : null; 
  
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
  }

  traerExpertos(): Observable<any> {
    return this.http.get<any[]>(this.apiURL, { headers: this.obtenerHeaders() });
  }

  crearExperto(experto: any): Observable<any> {
    return this.http.post<any>(this.apiURL, experto, { headers: this.obtenerHeaders() });
  }

  obtenerExpertoPorId(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiURL}/${id}`, { headers: this.obtenerHeaders() });
  }

  actualizarExperto(id: number, experto: any): Observable<any> {
    return this.http.put<any>(`${this.apiURL}/${id}`, experto, { headers: this.obtenerHeaders() });
  }

  borrarExpertoPorId(id: number): Observable<any>{
    return this.http.delete<any>(`${this.apiURL}/${id}`, { headers: this.obtenerHeaders() });
  }
}
