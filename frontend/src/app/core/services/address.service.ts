import { Injectable } from '@angular/core';
import { Address } from '../../shared/models/address.model';
import { HttpClientModule, HttpParams, HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AddressService {
  private headers = new HttpHeaders({'Content-Type': 'application/json',
    'Authorization': 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1ZCI6IndlYiIsInJvbGUiOiJST0xFX0FETUlOIiwiY3JlYXRlZCI6MTU4MDQ3NDAwNzI0NiwiaXNzIjoic3ByaW5nLXNlY3VyaXR5LWRlbW8iLCJleHAiOjE1ODA1MTAwMDcsImlhdCI6MTU4MDQ3NDAwN30.OTam0h0I8hKTF-zqc6HkJ5YtmBK5EftisLognJHpHphZImeRuP1cKyiqk9R-1YrWDvNp-KNRDDhSxD6ZZ2IOsg'
});

  constructor(
    private http: HttpClient
  ) { }

  add(newAddress: Address): Observable<any> {
    return this.http.post('http://localhost:8080/api/address', newAddress, {headers: this.headers, responseType: 'json'});
  }

  get(addressId: string): Observable<any> {
    return this.http.get('http://localhost:8080/api/address/'.concat(addressId), {headers: this.headers, responseType: 'json'});
  }

  update(updAddress: Address, addressId: string): Observable<any> {
    // const item = localStorage.getItem('user');
    // const decodedItem = JSON.parse(item);
    return this.http.put('http://localhost:8080/api/address/'.concat(addressId), updAddress, {headers: this.headers, responseType: 'json'});
  }
}
