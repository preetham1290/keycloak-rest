import { Injectable } from '@angular/core';
import { RequestOptionsArgs, Response, RequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { KeycloakHttp } from 'app/shared/keyclock/keycloak.http';

@Injectable()
export class GenericService {

  constructor(private http: KeycloakHttp) { }

  /**
     * Performs a request with `get` http method.
     */
  get(url: string, options?: RequestOptionsArgs): Observable<Response> {
    return this.http.get(url)
      .map((res: Response) => res)
      .catch((error: any) => Observable.throw(error));
  };
  /**
   * Performs a request with `post` http method.
   */
  post(url: string, body: Object, options?: RequestOptionsArgs): Observable<Response> {
    return this.http.post(url, body)
      .map((res: Response) => res)
      .catch((error: any) => Observable.throw(error.json()));
  }
  /**
   * Performs a request with `put` http method.
   */
  put(url: string, body: any, options?: RequestOptionsArgs): Observable<Response> {
    return this.http.put(url, body)
      .map((res: Response) => res)
      .catch((error: any) => Observable.throw(error.json()));
  }
  /**
   * Performs a request with `delete` http method.
   */
  delete(url: string, options?: RequestOptionsArgs): Observable<Response> {
    return this.http.delete(url)
      .map((res: Response) => res)
      .catch((error: any) => Observable.throw(error.json()));
  }

}
