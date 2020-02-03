import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToastrModule } from 'ngx-toastr';
import { MaterialModule } from './material/material.module';
import { AddAddressComponent } from './address/add-address/add-address.component';
import { AuthenticationModule } from './authentication/authentication.module';
import { UpdateAddressComponent } from './address/update-address/update-address.component';
import { AddressFormComponent } from './address/address-form/address-form.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AddressTableComponent } from './address/address-table/address-table.component';
import { AddressListComponent } from './address/address-list/address-list.component';
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


@NgModule({
  declarations: [
    AppComponent,
    AddAddressComponent,
    UpdateAddressComponent,
    AddressFormComponent,
    AddressTableComponent,
    AddressListComponent,
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
    GoogleChartsModule.forRoot()

  ],
  providers: [{provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true}],
  bootstrap: [AppComponent]
})
export class AppModule { }
