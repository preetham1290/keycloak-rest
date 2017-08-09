import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot } from '@angular/router';
import { Observable } from 'rxjs';
import { HttpGenericService } from 'app/shared/generic-service/http-generic.service';
import { environment } from '../environments/environment';
@Injectable()
export class InitResolveService implements Resolve<any>{

  constructor(private httpGenericService: HttpGenericService) { }
  resolve(route: ActivatedRouteSnapshot): Observable<any> | Promise<any> | any {
    return this.httpGenericService.get(environment.apiBaseUrl + 'public/kcinit');
  }
}
