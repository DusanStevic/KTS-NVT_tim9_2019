import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JwtHelperService } from '@auth0/angular-jwt';


@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private headers = new HttpHeaders({'Content-Type': 'application/json'});

  constructor(
    private http: HttpClient
  ) { }

  login(auth: any): Observable<any> {
    return this.http.post('http://localhost:8080/auth/login',
    {username: auth.username, password: auth.password}, {headers: this.headers, responseType: 'text'});
  }

  logout(): Observable<any> {
    return this.http.get('http://localhost:8080/auth/logout', {headers: this.headers, responseType: 'text'});
  }

  isLoggedIn(): boolean {
    if (localStorage.getItem('user')) {
        return true;
    }
    return false;
  }

  getToken(): string {
    return localStorage.getItem('user');
  }

  getRole(): string {
    const token = this.getToken();
    console.log(token);
    const jwt: JwtHelperService = new JwtHelperService();

    if (!token) {
      return 'NO_ROLE';
    }
    console.log(jwt.decodeToken(token).role);
    return jwt.decodeToken(token).role;
  }

}
