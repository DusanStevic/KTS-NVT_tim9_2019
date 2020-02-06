import { Injectable } from '@angular/core';
import { ReservationDetailed } from '../../shared/models/reservationDetailed.model';
import { HttpClientModule, HttpParams, HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ConstantsService } from './constants.service';

@Injectable({
  providedIn: 'root'
})
export class ReservationService {
  private headers = new HttpHeaders({'Content-Type': 'application/json'});
  constructor(
    private http: HttpClient,
    private constantsService: ConstantsService

  ) { }

  myReservations(): Observable<any> {
    return this.http.get( this.constantsService.reservationPath + '/myReservations', {headers: this.headers, responseType: 'json'});
  }
}
