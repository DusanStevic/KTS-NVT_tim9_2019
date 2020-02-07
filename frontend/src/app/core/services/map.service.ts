import { Injectable } from '@angular/core';
import { MapComponent } from 'src/app/location/map/map.component';
import { MatDialog } from '@angular/material';
import { ToastrService } from 'ngx-toastr';
import { LocationService } from './location.service';

@Injectable({
  providedIn: 'root'
})
export class MapService {

  constructor(
    public dialog: MatDialog,
    private locationService: LocationService,
    private toastr: ToastrService
  ) { }

  activateYandexMaps(locationId: string) {
    this.locationService.get(locationId).subscribe(
      (response => {

        if (response != null) {
          const dialogRef = this.dialog.open( MapComponent , {
            data: {latitude: response.latitude, longitude: response.longitude }
           });
        }
      }),
      (error => {
         this.toastr.error(error.error);
      }));

  }

}
