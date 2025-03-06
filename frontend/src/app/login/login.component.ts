import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { LoginService } from '../login.service';

@Component({
  selector: 'app-login',
  imports: [CommonModule, FormsModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent {
  username: string;
  password: string;

  constructor(private loginService:LoginService,private route:Router){
    this.username="";
    this.password="";
  }

  
  loguear(): void {
    this.loginService.login(this.username, this.password).subscribe(response => {
      if (response.funciona) {
        if (response.perfil === 'ROLE_ADMIN') {
          this.route.navigate(['/admin']);
        }

        if (response.perfil === 'ROLE_EXPERTO') {
          this.route.navigate(['/experto']);
        }

        
      } else {
        alert('Error en la autenticación');
      }
    });
  }
}
