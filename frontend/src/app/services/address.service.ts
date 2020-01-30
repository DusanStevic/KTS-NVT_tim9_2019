import { Injectable } from '@angular/core';
import { Address } from '../models/address.model';
import { HttpClientModule, HttpParams, HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AddressService {
  private headers = new HttpHeaders({'Content-Type': 'application/json'});

  constructor(
    private http: HttpClient
  ) { }

  add(newAddress: Address): Observable<any> {
    return this.http.post('api/address', newAddress, {headers: this.headers, responseType: 'json'});
  }
}
