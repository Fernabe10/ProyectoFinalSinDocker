import { Component } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { LogoComponent } from './logo/logo.component';
import { NavbarComponent } from './navbar/navbar.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  imports: [RouterOutlet,LogoComponent, NavbarComponent, CommonModule]
})
export class AppComponent {
  title = 'frontend';

  constructor(private route:Router){}

  mostrarNavbar(): boolean {
    return this.route.url !== '/' && this.route.url !== '/login';
  }
}
