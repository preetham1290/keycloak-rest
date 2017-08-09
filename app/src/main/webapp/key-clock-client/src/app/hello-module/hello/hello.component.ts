import { Component, OnInit } from '@angular/core';
import { KeycloakService } from 'app/shared/keyclock/keycloak.service';
import { GenericService } from 'app/shared/generic-service/generic.service';
import { HttpGenericService } from 'app/shared/generic-service/http-generic.service';

@Component({
  selector: 'app-hello',
  templateUrl: './hello.component.html',
  styleUrls: ['./hello.component.css']
})
export class HelloComponent implements OnInit {
  title = 'Angular2 Keyclock demo';
  pubData = null;
  secData = null;
  constructor(private genericService: GenericService, private httpGenericService: HttpGenericService) { }

  ngOnInit() {
    this.getSecuredData();
  }

  getPublicData() {
    this.pubData = null;
    this.httpGenericService.get('http://localhost:8080/auth/test').subscribe(res => {
      this.pubData = res;
    }, err => {
      console.log('err ', err);
    });
  }

  getSecuredData() {
    this.secData = null;
    this.genericService.get('http://localhost:8080/test').subscribe(res => {
      this.secData = res.json();
    }, err => {
      console.log('err ', err);
    });
  }

  logout() {
    KeycloakService.logout();
  }
}