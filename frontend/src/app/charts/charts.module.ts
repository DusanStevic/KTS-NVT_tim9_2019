import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaterialModule } from '../material/material.module';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { AgmCoreModule } from '@agm/core';
import { AppRoutingModule } from 'src/app/app-routing.module';

import { GoogleChartsModule } from 'angular-google-charts';
import { GoogleChartsComponent } from './google-charts/google-charts.component';
import { IncomeByEventsComponent } from './income-by-events/income-by-events.component';
import { IncomeByLocationsComponent } from './income-by-locations/income-by-locations.component';
import { TicketsSoldByEventsComponent } from './tickets-sold-by-events/tickets-sold-by-events.component';
import { TicketsSoldByLocationsComponent } from './tickets-sold-by-locations/tickets-sold-by-locations.component';
import { SystemInformationsComponent } from './system-informations/system-informations.component';



@NgModule({
  declarations: [
    GoogleChartsComponent,
    IncomeByEventsComponent,
    IncomeByLocationsComponent,
    TicketsSoldByEventsComponent,
    TicketsSoldByLocationsComponent,
    SystemInformationsComponent,
  ],
  imports: [
    CommonModule,
    MaterialModule,
    ReactiveFormsModule,
    FormsModule,
    AgmCoreModule,
    AppRoutingModule,
    GoogleChartsModule
  ],
  exports: [
    GoogleChartsComponent,
    IncomeByEventsComponent,
    IncomeByLocationsComponent,
    TicketsSoldByEventsComponent,
    TicketsSoldByLocationsComponent,
    SystemInformationsComponent,
  ]
})
export class ChartsModule { }
