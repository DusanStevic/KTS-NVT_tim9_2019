import { Component, OnInit } from '@angular/core';
import { MapService } from 'src/app/core/services/map.service';

@Component({
  selector: 'app-event-list',
  templateUrl: './event-list.component.html',
  styleUrls: ['./event-list.component.scss']
})
export class EventListComponent implements OnInit {

  constructor(
    private mapService: MapService
  ) { }

  ngOnInit() {
  }

  activateYandexMaps(addressId: string) {
    this.mapService.activateYandexMaps(addressId);
  }


}
