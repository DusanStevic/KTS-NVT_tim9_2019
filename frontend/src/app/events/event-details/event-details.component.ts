import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthenticationService } from 'src/app/core/services/authentication.service';
import { EventDetailed } from 'src/app/shared/models/eventDetailed.model';
import { EventService } from 'src/app/core/services/event.service';
import { ToastrService } from 'ngx-toastr';
import { MapService } from 'src/app/core/services/map.service';

@Component({
  selector: 'app-event-details',
  templateUrl: './event-details.component.html',
  styleUrls: ['./event-details.component.scss']
})
export class EventDetailsComponent implements OnInit {
  role: string;
  eventId: string;
  eventDetailed: EventDetailed;

  constructor(
    private route: ActivatedRoute,
    private authenticationService: AuthenticationService,
    private eventService: EventService,
    private toastr: ToastrService,
    private mapService: MapService,
    private router: Router,
  ) {this.initEvent(); }

  ngOnInit() {
    this.eventId = this.route.snapshot.paramMap.get('id');
    this.role = this.authenticationService.getRole();
  }

  initEvent() {
    this.eventService.getEvent(this.eventId).subscribe(
      success => {
        this.eventDetailed = success;
      },
      error => {
        this.toastr.error('An error occurred during event data retrieval');
      }
    );
  }

  activateYandexMaps(locationId: string) {
    this.mapService.activateYandexMaps(locationId);
  }

  back() {
    this.router.navigate(['events']);
  }

  reserve(id: string) {
    console.log(id);
    localStorage.setItem('selectedEventDay', id);
    this.router.navigate(['reserve']);
  }

}
