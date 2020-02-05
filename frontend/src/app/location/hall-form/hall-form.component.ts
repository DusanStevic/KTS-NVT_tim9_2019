import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { HallDTO } from 'src/app/shared/models/hall.model';

@Component({
  selector: 'app-hall-form',
  templateUrl: './hall-form.component.html',
  styleUrls: ['./hall-form.component.scss']
})
export class HallFormComponent implements OnInit {

  @Input() hall: HallDTO;
  @Input() hallForm: FormGroup;
  @Output() resetHallClicked: EventEmitter<any>;
  constructor() {
    this.resetHallClicked = new EventEmitter();
  }

  ngOnInit() {
  }

  onHallSubmit() {
    console.log('submit');
  }

  onResetHall() {
    console.log('reset');
    this.resetHallClicked.emit();
  }
}
