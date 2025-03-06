import { ActivatedRouteSnapshot, CanActivate, GuardResult, MaybeAsync, Router, RouterStateSnapshot} from '@angular/router';
import { LoginService } from './login.service';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})

export class expertoGuard implements CanActivate{
  constructor(private service:LoginService, private ruta:Router){

  }
  canActivate(
    route: ActivatedRouteSnapshot, 
    state: RouterStateSnapshot): boolean {
      var respuesta:boolean = false;
      if(this.service.getPerfil()=="ROLE_EXPERTO"){
        return respuesta = true;
      }else{
        this.ruta.navigate(['login']);
      }
      return respuesta;
      
  }
}
