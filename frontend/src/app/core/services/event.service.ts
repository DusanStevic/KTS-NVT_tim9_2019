import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { ConstantsService } from './constants.service';
import { Observable } from 'rxjs';
import { Search } from 'src/app/shared/models/search.model';
import { CreateEventDTO, EventUpdateDTO, EventSectorDTO } from 'src/app/shared/models/create-event.model';

@Injectable({
  providedIn: 'root'
})
export class EventService {

  private headers = new HttpHeaders({'Content-Type': 'application/json'});
  constructor(
    private http: HttpClient,
    private constantsService: ConstantsService
  ) {}

  getAll(): Observable<any> {
    return this.http.get(this.constantsService.eventPath + '/active', {headers: this.headers, responseType: 'json'});
  }

  search(search: Search): Observable <any> {
    return this.http.post(this.constantsService.eventPath + '/search', search, {headers: this.headers, responseType: 'json'});
  }

  add(newEvent: CreateEventDTO): Observable<any> {
    return this.http.post(this.constantsService.eventPath, newEvent, {headers: this.headers, responseType: 'json'});
  }

  getEvent(eventId: string): Observable<any> {
    return this.http.get(this.constantsService.eventPath + '/' + eventId, {headers: this.headers, responseType: 'json'});
  }


  get(eventId: string): Observable<any> {
    return this.http.get(this.constantsService.eventPath + '/' + eventId,
    {headers: this.headers, responseType: 'json'});
  }

  update(updEvent: EventUpdateDTO, eventId: string): Observable<any> {
    return this.http.put(this.constantsService.eventPath + '/' + eventId, updEvent,
    {headers: this.headers, responseType: 'json'});
  }

  addEventSector(eventId: string, newEventSector: EventSectorDTO): Observable<any> {
    return this.http.post(this.constantsService.eventSectorPath + '/' + eventId, newEventSector,
    {headers: this.headers, responseType: 'json'});
  }
}
