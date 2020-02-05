import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Location } from '../../shared/models/location.model';
import { Observable } from 'rxjs';
import { HallDTO } from 'src/app/shared/models/hall.model';
import { ConstantsService } from './constants.service';
@Injectable({
  providedIn: 'root'
})
export class LocationService {

  private headers = new HttpHeaders({'Content-Type': 'application/json'});
  constructor(
    private http: HttpClient,
    private constantsService: ConstantsService
  ) {}

  add(newLocation: Location): Observable<any> {
    return this.http.post(this.constantsService.locationPath, newLocation,
    {headers: this.headers, responseType: 'json'});
  }

  update(updLocation: Location, locationId: string): Observable<any> {
    return this.http.put(this.constantsService.locationPath + '/' + locationId, updLocation,
    {headers: this.headers, responseType: 'json'});
  }

  get(locationId: string): Observable<any> {
    return this.http.get(this.constantsService.locationPath + '/' + locationId,
    {headers: this.headers, responseType: 'json'});
  }

  getAll(): Observable<any> {
    return this.http.get(this.constantsService.locationPath,
    {headers: this.headers, responseType: 'json'});
  }

  delete(locationId: string): Observable<any> {
    return this.http.delete(this.constantsService.locationPath + '/' + locationId,
    {headers: this.headers, responseType: 'text'});
  }

  addHall(locationId: string, newHall: HallDTO): Observable<any> {
    return this.http.post(this.constantsService.hallPath + '/' + locationId, newHall,
    {headers: this.headers, responseType: 'json'});
  }

  deleteHall(hallId: string): Observable<any> {
    return this.http.delete(this.constantsService.hallPath + '/' + hallId,
    {headers: this.headers, responseType: 'text'});
  }

  getHall(hallId: string): Observable<any> {
    return this.http.get(this.constantsService.hallPath + '/' + hallId,
    {headers: this.headers, responseType: 'json'});
  }

  updateHall(hallId: string, updHall: HallDTO): Observable<any> {
    return this.http.put(this.constantsService.hallPath + '/' + hallId, updHall,
    {headers: this.headers, responseType: 'json'});
  }
}
