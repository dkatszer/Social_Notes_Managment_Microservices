import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {HttpClient, HttpErrorResponse, HttpHeaders} from '@angular/common/http';
import {AuthResponse} from '../model/auth-response';
import {LocalStorageKeys} from '../model/local-storage-keys.enum';
import {CurrUser} from '../model/curr-user';

@Injectable()
export class AuthenticationService {
  private authUri = '/token/auth';
  private refreshUri = '/token/refresh';
  private headers = new HttpHeaders({'Content-Type': 'application/json'});

  constructor(private http: HttpClient) {
  }

  login(username: string, password: string): Promise<boolean> {
    return this.http.post<AuthResponse>(this.authUri, JSON.stringify({
      username: username,
      password: password
    }), {headers: this.headers})
      .toPromise()
      .then(response => {
          // login successful if there's a service token in the response
          const isToken = response && response.token;
          if (isToken) {
            // store username and service token in local storage to keep user logged in between page refreshes
            localStorage.setItem(LocalStorageKeys.CURRENT_USER, JSON.stringify(new CurrUser(username, response.token)));

            // return true to indicate successful login
            return true;
          } else {
            // return false to indicate failed login
            return false;
          }
        },
        err => {
          console.log(err);
          return false;
        }
      );
  }

  isLoggedIn(): boolean {
    const token: String = this.getToken();
    return token && token.length > 0;
  }

  private getToken(): String {
    const currentUser = JSON.parse(localStorage.getItem(LocalStorageKeys.CURRENT_USER));
    const token = currentUser && currentUser.token;
    return token ? token : '';
  }

  logout(): void {
    // clear token remove user from local storage to log user out
    localStorage.removeItem(LocalStorageKeys.CURRENT_USER);
  }

}
