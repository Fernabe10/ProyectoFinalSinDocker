import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, GuardResult, MaybeAsync, Router, RouterStateSnapshot } from "@angular/router";
import { CanActivateFn } from '@angular/router';
import { LoginService } from './login.service';


@Injectable({
  providedIn: 'root'
})

export class adminGuard implements CanActivate{
  constructor(private service:LoginService, private ruta:Router){
    
  }

  canActivate(
    route: ActivatedRouteSnapshot, 
    state: RouterStateSnapshot): boolean {
      var respuesta:boolean = false;
      if(this.service.getPerfil()=="ROLE_ADMIN"){
        return respuesta = true;
      }else{
        this.ruta.navigate(['login']);
      }
      return respuesta;
      
  }
}
