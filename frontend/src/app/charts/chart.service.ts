import { Injectable } from '@angular/core';
import { HttpClientModule, HttpParams, HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

import {SystemInformations } from 'src/app/charts/model/systemInformations.model'

@Injectable({
  providedIn: 'root'
})
export class ChartService {

  private headers = new HttpHeaders({'Content-Type': 'application/json'});
  
  constructor(
    private http: HttpClient
  ) { }

  getSysInfo(): Observable<any> {
    return this.http.get("http://localhost:8080/api/charts/sysinfo", {headers: this.headers, responseType:'json'});
  }

  getIncomeByEvents(): Observable<any> {
    return this.http.get("http://localhost:8080/api/charts/event_incomes", {headers: this.headers, responseType:'json'});
  }

  getTicketsSoldByEvents(): Observable<any>{
    return this.http.get("http://localhost:8080/api/charts/event_tickets_sold", {headers: this.headers, responseType:'json'});
  }

  getIncomeByLocations(): Observable<any> {
    return this.http.get("http://localhost:8080/api/charts/location_incomes", {headers: this.headers, responseType:'json'});
  }

  getTicketsSoldByLocations(): Observable<any>{
    return this.http.get("http://localhost:8080/api/charts/location_tickets_sold", {headers: this.headers, responseType:'json'});
  }

}
