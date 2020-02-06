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
    return this.http.post('http://localhost:8080/api/address', newAddress, {headers: this.headers, responseType: 'json'});
  }

  get(addressId: string): Observable<any> {
    return this.http.get('http://localhost:8080/api/address/'.concat(addressId), {headers: this.headers, responseType: 'json'});
  }

  getAll(): Observable<any> {
    return this.http.get('http://localhost:8080/api/address', {headers: this.headers, responseType: 'json'});
  }
  update(updAddress: Address, addressId: string): Observable<any> {
    // const item = localStorage.getItem('user');
    // const decodedItem = JSON.parse(item);
    return this.http.put('http://localhost:8080/api/address/'.concat(addressId), updAddress, {headers: this.headers, responseType: 'json'});
  }

  delete(addressId: string): Observable<any> {
    return this.http.delete('http://localhost:8080/api/address/'.concat(addressId), {headers: this.headers, responseType: 'text'});
  }
}
