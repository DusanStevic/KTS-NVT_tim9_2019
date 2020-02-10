import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Location } from 'src/app/shared/models/location.model';
import { FormGroup } from '@angular/forms';

@Component({
  selector: 'app-location-form',
  templateUrl: './location-form.component.html',
  styleUrls: ['./location-form.component.scss']
})
export class LocationFormComponent implements OnInit {

  @Input() location: Location;
  @Input() locationForm: FormGroup;
  @Output() resetClicked: EventEmitter<any>;
  constructor() {
    this.resetClicked = new EventEmitter();
  }

  ngOnInit() {
  }

  onLocationSubmit() {
    console.log('submit');
  }
  onReset() {
    console.log('reset');
    this.resetClicked.emit();
  }
}
