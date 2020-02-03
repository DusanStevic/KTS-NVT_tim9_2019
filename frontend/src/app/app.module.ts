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
import { EventListComponent } from './events/event-list/event-list.component';
import { AngularYandexMapsModule } from 'angular8-yandex-maps';
import { GoogleChartsModule } from 'angular-google-charts';
import { AddressModule } from './address/address.module';
import { LocationModule } from './location/location.module';
import { ChartsModule } from './charts/charts.module';
import { UserModule } from './user/user.module';
import { BootstrapModule } from './material/bootstrap/bootstrap.module';
import { GoogleChartsComponent } from './charts/google-charts/google-charts.component';
import { IncomeByEventsComponent } from './charts/income-by-events/income-by-events.component';
import { IncomeByLocationsComponent } from './charts/income-by-locations/income-by-locations.component';
import { TicketsSoldByEventsComponent } from './charts/tickets-sold-by-events/tickets-sold-by-events.component';
import { TicketsSoldByLocationsComponent } from './charts/tickets-sold-by-locations/tickets-sold-by-locations.component';
import { SystemInformationsComponent } from './charts/system-informations/system-informations.component';
import { EventsModule } from './events/events.module';


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
      timeOut: 2500,
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
    EventsModule

  ],
  providers: [{provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true}],
  bootstrap: [AppComponent]
})
export class AppModule { }
