import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MyReservationsComponent } from './my-reservations/my-reservations.component';
import { MaterialModule } from '../material/material.module';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { AngularYandexMapsModule } from 'angular8-yandex-maps';
import { AppRoutingModule } from 'src/app/app-routing.module';
import { ViewReservationComponent } from './view-reservation/view-reservation.component';



@NgModule({
  declarations: [MyReservationsComponent, ViewReservationComponent],
  imports: [
    CommonModule,
    MaterialModule,
    ReactiveFormsModule,
    FormsModule,
    AngularYandexMapsModule,
    AppRoutingModule
  ],
  exports: [
    MyReservationsComponent
  ]
})
export class ReservationModule { }
