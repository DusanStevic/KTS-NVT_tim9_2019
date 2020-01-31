import { Component, OnInit } from '@angular/core';
import { Address } from 'src/app/shared/models/address.model';
import { AddressService } from 'src/app/core/services/address.service';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-address-list',
  templateUrl: './address-list.component.html',
  styleUrls: ['./address-list.component.scss']
})
export class AddressListComponent implements OnInit {

  addressList: Address[];
  constructor(
    private addressService: AddressService,
    private router: Router,
    private toastr: ToastrService
  ) { }

  ngOnInit() {
    console.log('ng on init address list ');
    this.addressService.getAll().subscribe(
      result => {
        console.log(result);
        this.addressList = result;
        console.log(this.addressList);
      }
    );
  }

  onDelete(id: string) {
    console.log(id);
    this.addressService.delete(id).subscribe(
      result => {
        this.toastr.success(result);
        console.log(result);
        console.log(result.body);
        this.addressList = this.addressList.filter(address => address.id !== id);
        console.log(this.addressList);
      }
    );
  }
}
