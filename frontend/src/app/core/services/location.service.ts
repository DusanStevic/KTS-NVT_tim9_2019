import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Location } from 'src/app/shared/models/location.model';
import { Observable } from 'rxjs';
import { Hall } from 'src/app/shared/models/hall.model';
@Injectable({
  providedIn: 'root'
})
export class LocationService {

  private headers = new HttpHeaders({'Content-Type': 'application/json'});
  constructor(
    private http: HttpClient
  ) {}

  add(newLocation: Location): Observable<any> {
    return this.http.post('http://localhost:8080/api/location', newLocation,
    {headers: this.headers, responseType: 'json'});
  }

  update(updLocation: Location, locationId: string): Observable<any> {
    return this.http.put('http://localhost:8080/api/location/'.concat(locationId), updLocation,
    {headers: this.headers, responseType: 'json'});
  }

  get(locationId: string): Observable<any> {
    return this.http.get('http://localhost:8080/api/location/'.concat(locationId),
    {headers: this.headers, responseType: 'json'});
  }

  getAll(): Observable<any> {
    return this.http.get('http://localhost:8080/api/location',
    {headers: this.headers, responseType: 'json'});
  }

  delete(locationId: string): Observable<any> {
    return this.http.delete('http://localhost:8080/api/location/'.concat(locationId),
    {headers: this.headers, responseType: 'text'});
  }

  addHall(locationId: string, newHall: Hall): Observable<any> {
    return this.http.post('http://localhost:8080/api/hall/'.concat(locationId), newHall,
    {headers: this.headers, responseType: 'json'});
  }
}
