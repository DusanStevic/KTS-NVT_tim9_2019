import { Component, OnInit } from '@angular/core';
import { Location } from 'src/app/shared/models/location.model';
import { LocationService } from 'src/app/core/services/location.service';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-location-list',
  templateUrl: './location-list.component.html',
  styleUrls: ['./location-list.component.scss']
})
export class LocationListComponent implements OnInit {

  locationList: Location[];
  constructor(
    private locationService: LocationService,
    private router: Router,
    private toastr: ToastrService
  ) { }

  ngOnInit() {
    console.log('list init');
    console.log('ng on init loc list ');
    this.locationService.getAll().subscribe(
      success => {
        console.log(success);
        this.locationList = success;
        console.log(this.locationList);
      },
      error => {
        this.toastr.error(error);
      }
    );
  }

  onDelete(id: string) {
    console.log(id);
    this.locationService.delete(id).subscribe(
      success => {
        this.toastr.success(success);
        console.log(success);
        console.log(success.body);
        this.locationList = this.locationList.filter(location => location.id !== id);
        console.log(this.locationList);
      },
      error => {
        this.toastr.error(error);
      }
    );
  }
}
