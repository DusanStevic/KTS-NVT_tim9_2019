import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ConstantsService } from './constants.service';

@Injectable({
  providedIn: 'root'
})
export class FileUploadService {
  private headers = new HttpHeaders({ 'Content-Type': 'application/json',
  'Access-Control-Allow-Origin': '*', 'Access-Control-Allow-Credentials': 'true'});


  constructor(
    private http: HttpClient,
    private constantsService: ConstantsService
  ) { }

  updateProfileImage(image: FormData): Observable<any> {
    return this.http.post(this.constantsService.filePath + '/profile-image', image, {headers: this.headers, responseType: 'json'});
  }
}
