import { Injectable } from '@angular/core';
import { HttpClientModule, HttpParams, HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ConstantsService } from './constants.service';

@Injectable({
  providedIn: 'root'
})
export class ChartService {

  private headers = new HttpHeaders({'Content-Type': 'application/json'});

  constructor(
    private http: HttpClient,
    private constantsService: ConstantsService
  ) { }

  getSysInfo(): Observable<any> {
    return this.http.get( this.constantsService.chartsPath + '/sysinfo', {headers: this.headers, responseType: 'json'});
  }

  getIncomeByEvents(): Observable<any> {
    return this.http.get( this.constantsService.chartsPath + '/event_incomes', {headers: this.headers, responseType: 'json'});
  }

  getTicketsSoldByEvents(): Observable<any> {
    return this.http.get( this.constantsService.chartsPath + '/event_tickets_sold', {headers: this.headers, responseType: 'json'});
  }

  getIncomeByLocations(): Observable<any> {
    return this.http.get( this.constantsService.chartsPath + '/location_incomes', {headers: this.headers, responseType: 'json'});
  }

  getTicketsSoldByLocations(): Observable<any> {
    return this.http.get( this.constantsService.chartsPath + '/location_tickets_sold', {headers: this.headers, responseType: 'json'});
  }

}
