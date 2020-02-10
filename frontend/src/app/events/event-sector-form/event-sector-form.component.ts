import { Component, OnInit, Input } from '@angular/core';
import { FormGroup } from '@angular/forms';

@Component({
  selector: 'app-event-sector-form',
  templateUrl: './event-sector-form.component.html',
  styleUrls: ['./event-sector-form.component.scss']
})
export class EventSectorFormComponent implements OnInit {

  @Input() eventSectorForm: FormGroup;
  constructor() { }

  ngOnInit() {
  }

  onEventSectorSubmit() {
    console.log('submit');
  }
}
