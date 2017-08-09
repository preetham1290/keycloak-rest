import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { Observable } from 'rxjs/Observable';
import { KeycloakService } from 'app/shared/keyclock/keycloak.service';
@Injectable()
export class RootAuthGuardGuard implements CanActivate {

  constructor(private router: Router) { };

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    if (KeycloakService.auth && KeycloakService.auth.loggedIn === true) {
      return true;
    }
    this.router.navigate(['']);
    return true;
  }
}
