import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MyReservationsComponent } from './my-reservations/my-reservations.component';
import { ReservationDetailedComponent } from './reservation-detailed/reservation-detailed.component';
import { MaterialModule } from '../material/material.module';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { AngularYandexMapsModule } from 'angular8-yandex-maps';
import { AppRoutingModule } from 'src/app/app-routing.module';



@NgModule({
  declarations: [MyReservationsComponent, ReservationDetailedComponent],
  imports: [
    CommonModule,
    MaterialModule,
    ReactiveFormsModule,
    FormsModule,
    AngularYandexMapsModule,
    AppRoutingModule
  ],
  exports: [
    MyReservationsComponent,
    ReservationDetailedComponent
  ]
})
export class ReservationModule { }
