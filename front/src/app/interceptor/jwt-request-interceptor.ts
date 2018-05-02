import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {LocalStorageKeys} from '../model/local-storage-keys.enum';
import {CurrUser} from '../model/curr-user';
import {catchError} from "rxjs/operators";

export class JwtRequestInterceptor implements HttpInterceptor {
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    // add authorization header with service token if available
    const json = JSON.parse(localStorage.getItem(LocalStorageKeys.CURRENT_USER));
    if (json) {
      const currentUser = Object.setPrototypeOf(json, CurrUser.prototype);
      if (currentUser && currentUser.token) {
        req = req.clone({
          setHeaders: {
            Authorization: `Bearer ${currentUser.token}`
          }
        });
      }
    }

    return next.handle(req)
      .pipe(
        catchError(error =>{
          if(error instanceof HttpErrorResponse && error.status===401){
            localStorage.removeItem(LocalStorageKeys.CURRENT_USER);
          }
          return Observable.throw(error);
        })
      );
  }

}
