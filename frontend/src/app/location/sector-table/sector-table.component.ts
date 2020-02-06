import { Component, OnInit, EventEmitter, Input, Output } from '@angular/core';
import { Sector } from 'src/app/shared/models/hall.model';
import { Router } from '@angular/router';
import { LocationService } from 'src/app/core/services/location.service';

@Component({
  selector: 'app-sector-table',
  templateUrl: './sector-table.component.html',
  styleUrls: ['./sector-table.component.scss']
})
export class SectorTableComponent implements OnInit {

  @Input() sectors: Sector[];
  @Output() deleteSectorClicked: EventEmitter<any>;
  displayedColumns: string[] = ['Name', 'Type', 'Edit', 'Delete'];
  constructor(
    private router: Router,
    private locationService: LocationService
  ) {
    this.deleteSectorClicked = new EventEmitter();
   }

  ngOnInit() {
  }

  updateSector(id: string) {
    console.log(id);
    localStorage.setItem('selectedSector', id);
    this.router.navigate(['sector/update']);
  }

  deleteSector(id: string) {
    console.log(id);
    this.deleteSectorClicked.emit(id);
  }

}
