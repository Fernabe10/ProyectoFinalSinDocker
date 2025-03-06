import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UsuariosService {
  private apiURL = 'http://localhost:9000/register';  

  constructor(private http: HttpClient) {}

  private obtenerHeaders(): HttpHeaders {
    const loginData = sessionStorage.getItem('LOGIN');
    const token = loginData ? JSON.parse(loginData).token : null; 
  
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
  }

  crearExperto(usuario: any): Observable<any> {
    return this.http.post<any>(this.apiURL, usuario, { headers: this.obtenerHeaders() });
  }
}
