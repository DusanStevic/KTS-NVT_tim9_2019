import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { EventUpdateDTO } from 'src/app/shared/models/create-event.model';
import { Router } from '@angular/router';
import { LocationService } from 'src/app/core/services/location.service';
import { EventService } from 'src/app/core/services/event.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-update-event',
  templateUrl: './update-event.component.html',
  styleUrls: ['./update-event.component.scss']
})
export class UpdateEventComponent implements OnInit {

  event: EventUpdateDTO;
  eventUpdForm: FormGroup;
  constructor(
    private fb: FormBuilder,
    private router: Router,
    private locationService: LocationService,
    private eventService: EventService,
    private toastr: ToastrService
  ) {
    this.event = {
      name: '',
      description: ''
    };
    this.createEventForm();
  }

  ngOnInit() {
    this.init();
  }

  createEventForm() {
    console.log(localStorage.getItem('selectedEvent'));
    this.eventUpdForm = this.fb.group({
      name: [this.event.name, Validators.required],
      description: [this.event.description]
    });
  }

  init() {
    this.eventService.get(localStorage.getItem('selectedEvent')).subscribe(
      result => {
        console.log(result);
        this.event = result;
        this.createEventForm();
      }
    );
  }

  onEventSubmit(e) {
    e.preventDefault();
    console.log('update ev');
    this.event = this.eventUpdForm.value;
    // this.address.id = 1;
    console.log(this.event);
    this.eventService.update(this.event, localStorage.getItem('selectedEvent')).subscribe(
      result => {
        this.toastr.success('Successfully updated event');
        console.log(result);
        this.router.navigate(['events']);
      }
    );
  }

  onReset(e: Event) {
    console.log('upd on reset');
    // this.router.navigate(['location/update']);
    console.log(this.eventUpdForm.value);
    this.createEventForm();
    console.log(this.eventUpdForm.value);
  }
}
