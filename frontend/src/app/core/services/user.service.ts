import { Injectable } from '@angular/core';
import { User } from '../../shared/models/user.model';
import { HttpClientModule, HttpParams, HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ConstantsService } from './constants.service';
import { Registration } from 'src/app/shared/models/registration.model';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private headers = new HttpHeaders({'Content-Type': 'application/json'});
  constructor(
    private http: HttpClient,
    private constantsService: ConstantsService

  ) { }

  updateUser(updUser: User): Observable<any> {
    return this.http.put( this.constantsService.userPath, updUser, {headers: this.headers, responseType: 'json'});
  }

  whoAmI(): Observable<any> {
    return this.http.get( this.constantsService.userPath + '/whoami', {headers: this.headers, responseType: 'json'});

  }

  addAdmin(addAdmin: Registration): Observable<any> {
    return this.http.post(this.constantsService.authenticationPath + '/registerAdmin', addAdmin,
      {headers: this.headers, responseType: 'json'});
  }

}
