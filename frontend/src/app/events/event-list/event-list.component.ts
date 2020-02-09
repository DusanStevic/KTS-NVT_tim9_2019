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
import { Search } from 'src/app/shared/models/search.model';
import { EventType } from 'src/app/shared/models/event-type.enum';

@Component({
  selector: 'app-event-list',
  templateUrl: './event-list.component.html',
  styleUrls: ['./event-list.component.scss']
})
export class EventListComponent implements OnInit {

  displayedColumns: string[] = ['name', 'startDate', 'endDate', 'eventType', 'details'];
  eventTypes = [null, EventType.SPORT, EventType.CULTURE, EventType.CONCERT, EventType.FESTIVAL, EventType.THEATRE];
  dataSource: MatTableDataSource<Event>;

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: true}) sort: MatSort;
  events: Event[];
  locations: Location[];
  searchForm: FormGroup;
  search: Search;
  errorMessage = '';

  constructor(
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
        console.log(this.events);
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

  submit() {
    this.search = this.searchForm.value;


    this.eventService.search(this.search as Search).subscribe(
      result => {
        this.errorMessage = '';
        this.events = result;
        this.dataSource = new MatTableDataSource<Event>(this.events);
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
        this.searchForm.reset();
      },
      error => {
        this.searchForm.reset();
        this.toastr.error('There was an error with your search request. Please try again later!');
        this.errorMessage = 'There was an error with your search request. Please try again later!';
      }
    );
  }
  reset() {
    this.searchForm.reset();
    this.initEvents();
  }




}
