import { Injectable } from '@angular/core';
import { HttpClientModule, HttpParams, HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ConstantsService } from './constants.service';

@Injectable({
  providedIn: 'root'
})
export class PaypalService {

  constructor(
    private http: HttpClient,
    private constantsService: ConstantsService
  ) { }

  makePayment(sum) {
    return this.http.post(this.constantsService.paypalPath + '/make/payment?sum=' + sum, {});
      //.map((response: Response) => response.json());
  }

  completePayment(paymentId, payerId) {
    return this.http.post(this.constantsService.paypalPath + '/complete/payment?paymentId=' + paymentId + '&payerId=' + payerId , {});
     // .map((response: Response) => response.json());
  }
}
