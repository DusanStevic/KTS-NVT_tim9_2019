import { Injectable } from '@angular/core';
import { ReservationDetailed } from '../../shared/models/reservationDetailed.model';
import { HttpClientModule, HttpParams, HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ConstantsService } from './constants.service';
import { ReservationDTO } from 'src/app/shared/models/reservation.model';

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

  getReservation( id: number): Observable<any> {
    return this.http.get( this.constantsService.reservationPath + '/myReservations/' + id , {headers: this.headers, responseType: 'json'});
  }

  puchaseReservation( id: number): Observable<any> {
    return this.http.put( this.constantsService.reservationPath + '/purchase/' + id , {headers: this.headers, responseType: 'json'});
  }

  cancelReservation( id: number): Observable<any> {
    return this.http.put( this.constantsService.reservationPath + '/cancel/' + id , {headers: this.headers, responseType: 'json'});
  }

  makeReservation(reservation: ReservationDTO): Observable<any> {
    return this.http.post( this.constantsService.reservationPath, reservation, {headers: this.headers, responseType: 'json'});
  }
}
