import { Component, OnInit } from '@angular/core';
import { Location } from 'src/app/shared/models/location.model';
import { LocationService } from 'src/app/core/services/location.service';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-location-details',
  templateUrl: './location-details.component.html',
  styleUrls: ['./location-details.component.scss']
})
export class LocationDetailsComponent implements OnInit {

  location: Location;
  constructor(
    private router: Router,
    private locationService: LocationService,
    private toastr: ToastrService
  ) {
    this.location = {
      name: '',
      description: '',
      address: '',
      id: '',
      latitude: NaN,
      longitude: NaN
    };
   }

  ngOnInit() {
    this.locationService.get(localStorage.getItem('selectedLocation')).subscribe(
      success => {
        console.log(success);
        this.location = success;
        console.log(this.location);
        console.log(this.location.id);
      },
      error => {
        this.toastr.error(error);
      }
    );
  }

}
