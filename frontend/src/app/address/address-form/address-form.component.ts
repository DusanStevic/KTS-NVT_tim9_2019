import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Address } from 'src/app/shared/models/address.model';

@Component({
  selector: 'app-address-form',
  templateUrl: './address-form.component.html',
  styleUrls: ['./address-form.component.scss']
})
export class AddressFormComponent implements OnInit {

  @Input() addressForm: FormGroup;
  @Input() address: Address;
  @Output() resetClicked: EventEmitter<any>;
  constructor() {
    this.resetClicked = new EventEmitter();
   }

  ngOnInit() {
  }

  onAddressSubmit( ) {
    console.log('submit');
  }

  onReset() {
    console.log('reset');
    this.resetClicked.emit();
  }
}
