import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LocationListComponent } from './location-list/location-list.component';
import { LocationFormComponent } from './location-form/location-form.component';
import { LocationTableComponent } from './location-table/location-table.component';
import { UpdateLocationComponent } from './update-location/update-location.component';
import { AddLocationComponent } from './add-location/add-location.component';
import { MaterialModule } from '../material/material.module';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { LocationDetailsComponent } from './location-details/location-details.component';
import { HallFormComponent } from './hall-form/hall-form.component';
import { MapComponent } from './map/map.component';
import { AngularYandexMapsModule } from 'angular8-yandex-maps';
import { HallTableComponent } from './hall-table/hall-table.component';
import { UpdateHallComponent } from './update-hall/update-hall.component';
import { SectorFormComponent } from './sector-form/sector-form.component';



@NgModule({
  declarations: [
    AddLocationComponent,
    UpdateLocationComponent,
    LocationTableComponent,
    LocationFormComponent,
    LocationListComponent,
    LocationDetailsComponent,
    HallFormComponent,
    MapComponent,
    HallTableComponent,
    UpdateHallComponent,
    SectorFormComponent
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
    LocationDetailsComponent,
    HallFormComponent,
    MapComponent
  ],
  entryComponents: [MapComponent],
})
export class LocationModule { }
