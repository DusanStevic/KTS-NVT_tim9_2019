import { Component, OnInit } from '@angular/core';
import { Address } from '../../shared/models/address.model';
import { ToastrService } from 'ngx-toastr';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { AddressService } from '../../core/services/address.service';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-update-address',
  templateUrl: './update-address.component.html',
  styleUrls: ['./update-address.component.scss']
})
export class UpdateAddressComponent implements OnInit {

  address: Address;
  addressUpdForm: FormGroup;
  constructor(
    private fb: FormBuilder,
    private router: Router,
    private addressService: AddressService,
    private route: ActivatedRoute,
    private toastr: ToastrService
  ) {
    this.address = {
      streetName: '',
      streetNumber: NaN,
      city: '',
      country: '',
      latitude: NaN,
      longitude: NaN
    };

    this.createForm();
  }

  ngOnInit() {
    console.log(localStorage.getItem('selectedAddress'));
    this.initAddress();
  }

  createForm() {
    console.log(localStorage.getItem('selectedAddress'));
    /*this.addressUpdForm = this.fb.group({
      streetName: ['', Validators.required],
      streetNumber: ['', Validators.required],
      city: ['', Validators.required],
      country: ['', Validators.required],
      latitude: ['', Validators.required],
      longitude: ['', Validators.required]
    });*/
    this.addressUpdForm = this.fb.group({
      streetName: [this.address.streetName, Validators.required],
      streetNumber: [this.address.streetNumber, Validators.required],
      city: [this.address.city, Validators.required],
      country: [this.address.country, Validators.required],
      latitude: [this.address.latitude, Validators.required],
      longitude: [this.address.longitude, Validators.required]
    });
  }

  initAddress() {
    this.addressService.get(localStorage.getItem('selectedAddress')).subscribe(
      result => {
        console.log(result);
        this.address = result;
        console.log(this.address);
        this.createForm();
      }
    );
  }
  onAddressSubmit(e) {
    e.preventDefault();
    console.log('update address');
    this.address = this.addressUpdForm.value;
    console.log(this.address);
    this.addressService.update(this.address as Address, localStorage.getItem('selectedAddress')).subscribe(
      result => {
        this.toastr.success(result);
        console.log(result);
        // this.router.navigate(['address/add']);
      }
    );
  }

  onReset(e) {
    console.log('upd on reset');
    // this.router.navigate(['address/update']);
    console.log(this.addressUpdForm.value);
    this.createForm();
    console.log(this.addressUpdForm.value);
  }
}
