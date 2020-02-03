import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthenticationService } from '../services/authentication.service';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {
    constructor(
        private authenticationService: AuthenticationService
      ) {}
    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

        const jwt = this.authenticationService.getToken();
        //console.log(jwt);

        if (jwt) {
            const cloned = req.clone({
                headers: req.headers.set('Authorization', 'Bearer ' + jwt)
            });

            return next.handle(cloned);
        } else {
            return next.handle(req);
        }
    }

}
