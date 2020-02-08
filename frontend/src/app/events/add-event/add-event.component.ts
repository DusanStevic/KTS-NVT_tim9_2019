import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { CreateEventDTO } from 'src/app/shared/models/create-event.model';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { EventService } from 'src/app/core/services/event.service';

@Component({
  selector: 'app-add-event',
  templateUrl: './add-event.component.html',
  styleUrls: ['./add-event.component.scss']
})
export class AddEventComponent implements OnInit {

  event: CreateEventDTO;
  eventForm: FormGroup;
  errorMessage = '';
  constructor(
    private fb: FormBuilder,
    private router: Router,
    private eventService: EventService,
    private toastr: ToastrService
  ) {
    this.createForm();
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
      endDate: undefined
    };
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
    console.log(this.event);
    this.eventService.add(this.event).subscribe(
      success => {
        this.toastr.success('Successfully added event');
        // this.errorMessage = 'Successfully added location';
        console.log(success);
        console.log(success.id);
        localStorage.setItem('selectedEvent', success.id);
        // this.router.navigate(['event/update']);
      },
      error => {
        this.toastr.error(error);
        this.errorMessage = 'Please enter valid data!';

      }
    );
  }

  onReset() {
    this.eventForm.reset();
  }

}
