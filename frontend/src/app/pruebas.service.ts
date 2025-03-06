import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PruebasService {

  private apiURL = 'http://localhost:9000/api/prueba';

  constructor(private http: HttpClient) { }

  private obtenerHeaders(): HttpHeaders {
    const loginData = sessionStorage.getItem('LOGIN');
    const token = loginData ? JSON.parse(loginData).token : null; 
  
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
  }

  actualizarPrueba(id: number, prueba: any, archivo?: File): Observable<any> {
    const formData = new FormData();
    formData.append('puntuacionMaxima', prueba.puntuacionMaxima);
    formData.append('idEspecialidad', prueba.especialidad.idEspecialidad);
  
    if (archivo) {
      formData.append('file', archivo);
    }
  
    return this.http.put<any>(`${this.apiURL}/${id}`, formData, { headers: this.obtenerHeaders() });
  }
  
  traerPruebas(): Observable<any> {
    return this.http.get<any[]>(this.apiURL, { headers: this.obtenerHeaders() });
  }

  crearPrueba(formData: FormData): Observable<any> {
    return this.http.post<any>(this.apiURL, formData, { headers: this.obtenerHeaders() });
  }

  obtenerPruebaPorId(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiURL}/${id}`, { headers: this.obtenerHeaders() });
  }

  agregarItem(item: any): Observable<any> {
    return this.http.post<any>(`http://localhost:9000/api/item`, item, { headers: this.obtenerHeaders() });
  }

  borrarPruebaPorId(id: number): Observable<any>{
    return this.http.delete<any>(`${this.apiURL}/${id}`, { headers: this.obtenerHeaders() });
  }

}
