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
  @Input() isEventUpdate: boolean;
  @Output() deleteSectorClicked: EventEmitter<any>;
  @Output() addEventSectorClicked: EventEmitter<any>;
  displayedColumns: string[];
  constructor(
    private router: Router,
    private locationService: LocationService
  ) {
    this.deleteSectorClicked = new EventEmitter();
    this.addEventSectorClicked = new EventEmitter();
   }

  ngOnInit() {
    if (this.isEventUpdate) {
      this.displayedColumns = ['Name', 'Type', 'Add'];
    } else {
      this.displayedColumns = ['Name', 'Type', 'Edit', 'Delete'];
    }
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

  addEventSector(id: string, name: string) {
    console.log('selected sector id: ' + id);
    this.addEventSectorClicked.emit({sectorId: id, sectorName: name});
  }
}
