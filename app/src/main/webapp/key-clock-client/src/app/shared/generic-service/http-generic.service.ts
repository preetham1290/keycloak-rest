import { Injectable } from '@angular/core';
import { RequestOptionsArgs, Response, RequestOptions, Http } from '@angular/http';
import { Observable } from 'rxjs/Rx';

@Injectable()
export class HttpGenericService {

  constructor(private http: Http) { }

  /**
    * Performs a request with `get` http method.
    */
  get(url: string, options?: RequestOptionsArgs): Observable<Response> {
    return this.http.get(url)
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw(error));
  };
  /**
   * Performs a request with `post` http method.
   */
  post(url: string, body: Object, options?: RequestOptionsArgs): Observable<Response> {
    return this.http.post(url, body)
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw(error.json()));
  }
  /**
   * Performs a request with `put` http method.
   */
  put(url: string, body: any, options?: RequestOptionsArgs): Observable<Response> {
    return this.http.put(url, body)
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw(error.json()));
  }
  /**
   * Performs a request with `delete` http method.
   */
  delete(url: string, options?: RequestOptionsArgs): Observable<Response> {
    return this.http.delete(url)
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw(error.json()));
  }

}
