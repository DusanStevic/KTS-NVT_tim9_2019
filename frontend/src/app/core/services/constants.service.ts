import { Injectable } from '@angular/core';

@Injectable()
export class ConstantsService {
    readonly localhost = 'http://localhost:8080';
    readonly authenticationPath = 'http://localhost:8080/auth';
    readonly chartsPath = 'http://localhost:8080/api/charts';
    readonly userPath = 'http://localhost:8080/api/user';
    readonly filePath = 'http://localhost:8080/media';
    readonly locationPath = 'http://localhost:8080/api/location';
    readonly hallPath = 'http://localhost:8080/api/hall';
    readonly sectorPath = 'http://localhost:8080/api/sector';
    readonly reservationPath = 'http://localhost:8080/api/reservation';
    readonly paypalPath = 'http://localhost:8080/paypal';
}

