import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  token: string;
  perfil: string;
  logeado: boolean;
  usuario: any;
  especialidadId: number;
  idUser: number;

  constructor(private http: HttpClient) {
    this.token = "";
    this.perfil = "";
    this.logeado = false;
    this.usuario = {};
    this.especialidadId = 0;
    this.idUser = 0;
    this.recuperar();
  }

  // Método para almacenar los datos en sessionStorage
  private almacenar(): void {
    const objeto = {
      token: this.token,
      perfil: this.perfil,
      logeado: this.logeado,
      usuario: this.usuario,
      especialidadId: this.especialidadId,
      idUser: this.idUser
    };
    sessionStorage.setItem('LOGIN', JSON.stringify(objeto));
  }

  // Método para recuperar los datos almacenados en sessionStorage
  private recuperar(): void {
    const cadena = sessionStorage.getItem('LOGIN');
    if (cadena) {
      const objeto = JSON.parse(cadena);
      this.token = objeto.token;
      this.perfil = objeto.perfil;
      this.logeado = objeto.logeado;
      this.usuario = objeto.usuario;
      this.especialidadId = objeto.especialidadId;
      this.idUser = objeto.idUser;
    } else {
      this.token = "";
      this.perfil = "";
      this.logeado = false;
      this.usuario = {};
      this.especialidadId = 0;
      this.idUser = 0;
    }
  }

  // Método para realizar el login
  login(username: string, password: string): Observable<any> {
    const loginData = { username, password };

    return this.http.post<any>('http://localhost:9000/login', loginData)
      .pipe(
        map((data: any) => {
          if (data.token) {
            this.token = data.token;
            this.perfil = data.authorities[0]; 
            this.usuario = { username: data.username };
            this.logeado = true;
            this.especialidadId = data.especialidadId ?? 0;
            this.idUser = data.idUser;
            this.almacenar();
            return { funciona: true, perfil: this.perfil };
          } else {
            return { funciona: false };
          }
        })
      );
  }

  // Método para cerrar sesión
  logout(): void {
    this.token = "";
    this.perfil = "";
    this.logeado = false;
    this.usuario = {};
    this.especialidadId = 0;
    this.idUser = 0;
    sessionStorage.removeItem('LOGIN');
  }

  // Método para verificar si el usuario está logeado
  isLogged(): boolean {
    return this.logeado;
  }

  //Metodos para traer datos del usuario logeado
  getNombre(): string {
    const cont = sessionStorage.getItem("LOGIN");
    return cont ? JSON.parse(cont).usuario.username || "" : "";
  }

  getPerfil(): string {
    const cont = sessionStorage.getItem("LOGIN");
    return cont ? JSON.parse(cont).perfil || "" : "";
  }

  getEspecialidad(): number {
    const cont = sessionStorage.getItem("LOGIN");
    return cont ? JSON.parse(cont).especialidadId ?? 0 : 0;
  }

  getIdUser(): number {
    const cont = sessionStorage.getItem("LOGIN");
    return cont ? JSON.parse(cont).idUser ?? 0 : 0;
  }
}
