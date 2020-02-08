import { Component, OnInit, ViewChild } from '@angular/core';
import { MapService } from 'src/app/core/services/map.service';
import { Event } from 'src/app/shared/models/event.model';
import { Location } from 'src/app/shared/models/location.model';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { EventService } from 'src/app/core/services/event.service';
import { ToastrService } from 'ngx-toastr';
import { LocationService } from 'src/app/core/services/location.service';
import { MatPaginator, MatTableDataSource, MatSort } from '@angular/material';

@Component({
  selector: 'app-event-list',
  templateUrl: './event-list.component.html',
  styleUrls: ['./event-list.component.scss']
})
export class EventListComponent implements OnInit {

  displayedColumns: string[] = ['name', 'startDate', 'endDate', 'eventType', 'details'];
  dataSource: MatTableDataSource<Event>;

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: true}) sort: MatSort;
  events: Event[];
  locations: Location[];
  searchForm: FormGroup;

  constructor(
    private mapService: MapService,
    private fb: FormBuilder,
    private router: Router,
    private eventService: EventService,
    private locationService: LocationService,
    private toastr: ToastrService


  ) { }



  ngOnInit() {
    this.initEvents();
  }

  initEvents() {
    this.eventService.getAll().subscribe(
      success => {
        this.events = success;
        this.dataSource = new MatTableDataSource<Event>(this.events);
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
      },
      error => {
        this.toastr.error('An error occurred during events data retrieval');
      }
    );
    this.locationService.getAll().subscribe(
      success => {
        this.locations = success;
      },
      error => {
        this.toastr.error('An error occurred during locations data retrieval');
      }
    );
    this.createForm();
  }


  createForm(): void {
    this.searchForm = this.fb.group({
      name : [null, ],
      startDate: [null, Validators.required],
      endDate: [null, Validators.required],
      eventType: [null, ],
      locationId: [null, ],
    });
  }

  activateYandexMaps(locationId: string) {
    this.mapService.activateYandexMaps(locationId);
  }


}
