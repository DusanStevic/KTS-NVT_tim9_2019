import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { CreateEventDTO } from 'src/app/shared/models/create-event.model';
import { Sector } from 'src/app/shared/models/hall.model';

@Component({
  selector: 'app-event-form',
  templateUrl: './event-form.component.html',
  styleUrls: ['./event-form.component.scss']
})
export class EventFormComponent implements OnInit {

  @Input() event: CreateEventDTO;
  @Input() eventForm: FormGroup;
  @Input() hideFormFields: boolean;
  @Output() resetClicked: EventEmitter<any>;
  constructor() {
    this.resetClicked = new EventEmitter();
  }

  ngOnInit() {
    console.log(this.hideFormFields);
  }

  onEventSubmit() {
    console.log('submit');
  }
  onReset() {
    console.log('reset');
    this.resetClicked.emit();
  }
}
