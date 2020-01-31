import { Injectable } from '@angular/core';
import { Address } from '../../shared/models/address.model';
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

  get(addressId: string): Observable<any> {
    return this.http.get('api/address/'.concat(addressId), {headers: this.headers, responseType: 'json'});
  }

  update(updAddress: Address): Observable<any> {
    return this.http.put('api/address/', updAddress, {headers: this.headers, responseType: 'json'});
  }
}
