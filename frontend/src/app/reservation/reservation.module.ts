import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MyReservationsComponent } from './my-reservations/my-reservations.component';
import { MaterialModule } from '../material/material.module';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { AngularYandexMapsModule } from 'angular8-yandex-maps';
import { AppRoutingModule } from 'src/app/app-routing.module';
import { ViewReservationComponent, ConfirmCancelReservationDialog } from './view-reservation/view-reservation.component';
import { MakeReservationComponent } from './make-reservation/make-reservation.component';
import { LocationModule } from '../location/location.module';
import { SectorChartComponent } from '../location/sector-chart/sector-chart.component';



@NgModule({
  declarations: [MyReservationsComponent, ViewReservationComponent, ConfirmCancelReservationDialog, MakeReservationComponent],
  imports: [
    CommonModule,
    MaterialModule,
    ReactiveFormsModule,
    FormsModule,
    AngularYandexMapsModule,
    AppRoutingModule,
    LocationModule
  ],

  entryComponents: [
    ConfirmCancelReservationDialog,
    SectorChartComponent
  ],
  exports: [
    MyReservationsComponent, ViewReservationComponent, ConfirmCancelReservationDialog, MakeReservationComponent
  ]
})
export class ReservationModule { }
