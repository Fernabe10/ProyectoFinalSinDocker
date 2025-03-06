import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EvaluacionItemService {

  private apiURL = 'http://localhost:9000/api/evaluacionitem';
  
  constructor(private http:HttpClient) { }

  private obtenerHeaders(): HttpHeaders {
    const loginData = sessionStorage.getItem('LOGIN');
    const token = loginData ? JSON.parse(loginData).token : null; 
  
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
  }

  crearEvaluacionItem(evaluacionItem: any): Observable<any> {
    return this.http.post<any>(this.apiURL, evaluacionItem, { headers: this.obtenerHeaders() });
  }
}
