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
import { SectorTableComponent } from './sector-table/sector-table.component';
import { UpdateSectorComponent } from './update-sector/update-sector.component';
import { SectorChartComponent } from './sector-chart/sector-chart.component';
import { TooltipModule } from 'ngx-bootstrap';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
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
    SectorFormComponent,
    SectorTableComponent,
    UpdateSectorComponent,
    SectorChartComponent
  ],
  imports: [
    CommonModule,
    MaterialModule,
    ReactiveFormsModule,
    FormsModule,
    AngularYandexMapsModule,
    NgbModule,
    TooltipModule.forRoot()
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
