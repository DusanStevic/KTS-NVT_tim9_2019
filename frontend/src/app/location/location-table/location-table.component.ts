import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Location } from 'src/app/shared/models/location.model';
import { Router } from '@angular/router';
import { LocationService } from 'src/app/core/services/location.service';

@Component({
  selector: 'app-location-table',
  templateUrl: './location-table.component.html',
  styleUrls: ['./location-table.component.scss']
})
export class LocationTableComponent implements OnInit {

  @Input() locations: Location[];
  @Output() deleteLocClicked: EventEmitter<any>;
  displayedColumns: string[] = ['Name', 'Description', 'Update', 'Delete'];
  constructor(
    private router: Router,
    private locationService: LocationService
  ) {
    this.deleteLocClicked = new EventEmitter();
   }

  ngOnInit() {
  }

  updateLocation(id: string) {
    console.log(id);
    localStorage.setItem('selectedLocation', id);
    this.router.navigate(['location/update']);
  }

  deleteLocation(id: string) {
    console.log(id);
    this.deleteLocClicked.emit(id);
  }
}
