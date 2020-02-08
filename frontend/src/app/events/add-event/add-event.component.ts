import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { CreateEventDTO } from 'src/app/shared/models/create-event.model';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { EventService } from 'src/app/core/services/event.service';
import { LocationService } from 'src/app/core/services/location.service';
import { Sector } from 'src/app/shared/models/hall.model';

@Component({
  selector: 'app-add-event',
  templateUrl: './add-event.component.html',
  styleUrls: ['./add-event.component.scss']
})
export class AddEventComponent implements OnInit {

  event: CreateEventDTO;
  eventForm: FormGroup;
  errorMessage = '';
  hideFormFields = false;
  constructor(
    private fb: FormBuilder,
    private router: Router,
    private eventService: EventService,
    private locationService: LocationService,
    private toastr: ToastrService
  ) {
   }

  ngOnInit() {
    this.event = {
      name: '',
      description: '',
      eventType: NaN,
      locationId: '',
      numDays: NaN,
      maxTickets: NaN,
      startDate: undefined,
      endDate: undefined,
      hallId: ''
    };
    this.createForm();
  }

  createForm() {
    console.log('create form');
    this.eventForm = this.fb.group({
      name: ['', Validators.required],
      description: [''],
      eventType: ['', Validators.required],
      numDays: ['', Validators.required],
      maxTickets: ['', Validators.required],
      startDate: ['', Validators.required],
      endDate: ['', Validators.required]
    });
  }

  onEventSubmit(e) {
    e.preventDefault();
    this.event = this.eventForm.value;
    this.event.locationId = localStorage.getItem('selectedLocation');
    this.event.hallId = localStorage.getItem('selectedHall');
    console.log(this.event);
    this.eventService.add(this.event).subscribe(
      success => {
        this.toastr.success('Successfully added event');
        // this.errorMessage = 'Successfully added location';
        console.log(success);
        console.log(success.id);
        localStorage.setItem('selectedEvent', success.id);
        this.router.navigate(['events/update']);
      },
      error => {
        console.log(error);
        this.toastr.error('Please enter valid data!');
        this.errorMessage = 'Please enter valid data!';

      }
    );
  }

  onReset() {
    this.eventForm.reset();
  }

}
