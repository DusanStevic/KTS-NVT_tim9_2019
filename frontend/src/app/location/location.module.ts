import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LocationListComponent } from './location-list/location-list.component';
import { LocationFormComponent } from './location-form/location-form.component';
import { LocationTableComponent } from './location-table/location-table.component';
import { UpdateLocationComponent } from './update-location/update-location.component';
import { AddLocationComponent } from './add-location/add-location.component';
import { MaterialModule } from '../material/material.module';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { MapComponent } from './map/map.component';
import { AngularYandexMapsModule } from 'angular8-yandex-maps';



@NgModule({
  declarations: [
    AddLocationComponent,
    UpdateLocationComponent,
    LocationTableComponent,
    LocationFormComponent,
    LocationListComponent,
    MapComponent
  ],
  imports: [
    CommonModule,
    MaterialModule,
    ReactiveFormsModule,
    FormsModule,
    AngularYandexMapsModule
  ],
  exports: [
    UpdateLocationComponent,
    LocationFormComponent,
    LocationTableComponent,
    LocationListComponent,
    AddLocationComponent,
    MapComponent
  ]
})
export class LocationModule { }
