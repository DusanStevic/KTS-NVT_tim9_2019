import { Component, OnInit } from '@angular/core';
import { Location } from 'src/app/shared/models/location.model';
import { LocationService } from 'src/app/core/services/location.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-location-details',
  templateUrl: './location-details.component.html',
  styleUrls: ['./location-details.component.scss']
})
export class LocationDetailsComponent implements OnInit {

  location: Location;
  constructor(
    private router: Router,
    private locationService: LocationService
  ) {
    this.location = {
      name: '',
      description: '',
      addressId: NaN,
      id: ''
    };
   }

  ngOnInit() {
    this.locationService.get(localStorage.getItem('selectedLocation')).subscribe(
      result => {
        console.log(result);
        this.location = result;
        console.log(this.location);
        console.log(this.location.id);
      }
    );
  }

}
