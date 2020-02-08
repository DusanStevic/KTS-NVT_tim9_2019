import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { ConstantsService } from './constants.service';
import { Observable } from 'rxjs';
import { Search } from 'src/app/shared/models/search.model';

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
}
