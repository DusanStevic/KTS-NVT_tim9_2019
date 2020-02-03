import { Injectable } from '@angular/core';
import { User } from '../../shared/models/user.model';
import { HttpClientModule, HttpParams, HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ConstantsService } from './constants.service';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private headers = new HttpHeaders({'Content-Type': 'application/json'});
  constructor(
    private http: HttpClient,
    private constantsService: ConstantsService

  ) { }

  whoAmI(): Observable<any> {
    return this.http.get( this.constantsService.userPath + '/whoami', {headers: this.headers, responseType: 'json'});

  }
}
