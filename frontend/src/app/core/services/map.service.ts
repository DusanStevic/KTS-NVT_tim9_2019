import { Injectable } from '@angular/core';
import { MapComponent } from 'src/app/location/map/map.component';
import { MatDialog } from '@angular/material';
import { AddressService } from './address.service';
import { ToastrService } from 'ngx-toastr';

@Injectable({
  providedIn: 'root'
})
export class MapService {

  constructor(
    public dialog: MatDialog,
    private addressService: AddressService,
    private toastr: ToastrService
  ) { }

  activateYandexMaps(addressId: string) {
    this.addressService.get(addressId).subscribe(
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
