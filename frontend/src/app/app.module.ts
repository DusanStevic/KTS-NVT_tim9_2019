import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToastrModule } from 'ngx-toastr';
import { MaterialModule } from './material/material.module';
import { AuthenticationModule } from './authentication/authentication.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { CoreModule } from './core/core.module';
import { SharedModule } from './shared/shared.module';
import { JwtInterceptor } from './core/interceptors/jwt-interceptor.interceptor';
import { AngularYandexMapsModule } from 'angular8-yandex-maps';
import { GoogleChartsModule } from 'angular-google-charts';
import { AddressModule } from './address/address.module';
import { LocationModule } from './location/location.module';
import { ChartsModule } from './charts/charts.module';
import { UserModule } from './user/user.module';
import { BootstrapModule } from './material/bootstrap/bootstrap.module';
import { EventsModule } from './events/events.module';
import { ReservationModule } from './reservation/reservation.module';


@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModule,
    BootstrapModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    ToastrModule.forRoot({
      progressBar: true,
      timeOut: 4000,
      closeButton: true,
      positionClass: 'toast-bottom-right',
      preventDuplicates: true
    }),
    AuthenticationModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    CoreModule,
    SharedModule,
    AddressModule,
    LocationModule,
    UserModule,
    GoogleChartsModule.forRoot(),
    ChartsModule,
    AngularYandexMapsModule.forRoot('18116907-79b6-47b3-97aa-0db7c335b7e0'),
    EventsModule,
    ReservationModule

  ],
  providers: [{provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true}],
  bootstrap: [AppComponent]
})
export class AppModule { }
