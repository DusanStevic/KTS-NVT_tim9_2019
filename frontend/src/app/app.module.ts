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
import { GoogleChartsModule } from 'angular-google-charts';
import { GoogleChartsComponent } from './charts/google-charts/google-charts.component';
import { IncomeByEventsComponent } from './charts/income-by-events/income-by-events.component';
import { IncomeByLocationsComponent } from './charts/income-by-locations/income-by-locations.component';
import { TicketsSoldByEventsComponent } from './charts/tickets-sold-by-events/tickets-sold-by-events.component';
import { TicketsSoldByLocationsComponent } from './charts/tickets-sold-by-locations/tickets-sold-by-locations.component';
import { SystemInformationsComponent } from './charts/system-informations/system-informations.component';
import { EventListComponent } from './events/event-list/event-list.component';
import { AngularYandexMapsModule } from 'angular8-yandex-maps';
import { AddressModule } from './address/address.module';
import { LocationModule } from './location/location.module';


@NgModule({
  declarations: [
    AppComponent,
    GoogleChartsComponent,
    IncomeByEventsComponent,
    IncomeByLocationsComponent,
    TicketsSoldByEventsComponent,
    TicketsSoldByLocationsComponent,
    SystemInformationsComponent,
    EventListComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    ToastrModule.forRoot({
      timeOut: 3000,
      closeButton: true,
      positionClass: 'toast-top-right',
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
    GoogleChartsModule.forRoot(),
    AngularYandexMapsModule.forRoot('18116907-79b6-47b3-97aa-0db7c335b7e0')

  ],
  providers: [{provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true}],
  bootstrap: [AppComponent]
})
export class AppModule { }
