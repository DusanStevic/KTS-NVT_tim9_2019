import { Component, OnInit, Input, Output, EventEmitter, ViewChild } from '@angular/core';
import { Hall } from 'src/app/shared/models/hall.model';
import { Router } from '@angular/router';
import { LocationService } from 'src/app/core/services/location.service';

@Component({
  selector: 'app-hall-table',
  templateUrl: './hall-table.component.html',
  styleUrls: ['./hall-table.component.scss']
})
export class HallTableComponent implements OnInit {

  @Input() halls: Hall[];
  @Output() deleteHallClicked: EventEmitter<any>;
  displayedColumns: string[] = ['Name', 'Sitting Sectors', 'Standing Sectors', 'Edit', 'Delete'];
  constructor(
    private router: Router,
    private locationService: LocationService
  ) {
    this.deleteHallClicked = new EventEmitter();
  }

  ngOnInit() {
  }

  getNrOfSectors(type: string, hall: Hall): number {
    return hall.sectors.filter(sector => sector.type === type).length;
  }
  updateHall(id: string) {
    console.log(id);
    localStorage.setItem('selectedHall', id);
    this.router.navigate(['hall/update']);
  }

  deleteHall(id: string) {
    console.log(id);
    this.deleteHallClicked.emit(id);
  }
}
