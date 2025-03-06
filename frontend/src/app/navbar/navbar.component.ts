import { Component, OnInit } from '@angular/core';
import { LoginService } from '../login.service'; 
import { CommonModule} from '@angular/common';
import { Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css'],
  imports:[CommonModule, RouterLink]
})
export class NavbarComponent implements OnInit {
  perfil: string = '';

  constructor(private route:Router,private loginService: LoginService) {}

  ngOnInit(): void {
    
    this.perfil = this.loginService.perfil || ''; 
  }

  login(){
    this.route.navigate(['/login']);
  }

  isLogged(): boolean {
    return this.loginService.isLogged();
  }

  logout(): void {
    this.loginService.logout();
    window.location.href = '/login';
  }

  getNombre(){
    return this.loginService.getNombre();
  }
}
