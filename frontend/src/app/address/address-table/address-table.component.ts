import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Address } from 'src/app/shared/models/address.model';
import { Router } from '@angular/router';
import { AddressService } from 'src/app/core/services/address.service';

@Component({
  selector: 'app-address-table',
  templateUrl: './address-table.component.html',
  styleUrls: ['./address-table.component.scss']
})
export class AddressTableComponent implements OnInit {

  @Input() addresses: Address[];
  @Output() deleteClicked: EventEmitter<any>;
  displayedColumns: string[] = ['Street', 'Number', 'City', 'Country', 'Latitude', 'Longitude', 'Update', 'Delete'];
  constructor(
    private router: Router,
    private addressService: AddressService
  ) {
    this.deleteClicked = new EventEmitter();
  }

  ngOnInit() {
  }

  updateAddress(id: string) {
    console.log(id);
    localStorage.setItem('selectedAddress', id);
    this.router.navigate(['address/update']);
  }

  deleteAddress(id: string) {
    console.log(id);
    this.deleteClicked.emit(id);
  }
}
