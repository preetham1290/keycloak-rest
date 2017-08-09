import { Component, OnInit } from '@angular/core';
import { KeycloakService } from 'app/shared/keyclock/keycloak.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-wrapper',
  templateUrl: './wrapper.component.html'
})
export class WrapperComponent implements OnInit {

  constructor(private router: Router, private activatedRoute: ActivatedRoute) {
  }

  ngOnInit() {
    let kcInit = this.activatedRoute.snapshot.data['kcInit'];
    KeycloakService.init(kcInit).then(res => {
      if (KeycloakService.auth && KeycloakService.auth.loggedIn === true) {
        this.router.navigate(['/home']);
        return true;
      }
    }, err => {
      console.log(err);
    });
  }

}
