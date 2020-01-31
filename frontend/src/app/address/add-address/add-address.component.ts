import { Component, OnInit } from '@angular/core';
import { Address } from '../../shared/models/address.model';
import { ToastrService } from 'ngx-toastr';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { AddressService } from '../../core/services/address.service';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-add-address',
  templateUrl: './add-address.component.html',
  styleUrls: ['./add-address.component.scss']
})
export class AddAddressComponent implements OnInit {

  address: Address;
  addressForm: FormGroup;
  constructor(
    private fb: FormBuilder,
    private router: Router,
    private addressService: AddressService,
    private route: ActivatedRoute,
    private toastr: ToastrService
  ) { this.createForm(); }

  ngOnInit() {
    this.address = {
      streetName: '',
      streetNumber: NaN,
      city: '',
      country: '',
      latitude: NaN,
      longitude: NaN
    };
  }

  createForm() {
    this.addressForm = this.fb.group({
      streetName: ['', Validators.required],
      streetNumber: ['', Validators.required],
      city: ['', Validators.required],
      country: ['', Validators.required],
      latitude: ['', Validators.required],
      longitude: ['', Validators.required]
    });
  }

  onAddressSubmit(e) {
    e.preventDefault();
    console.log('add address');
    this.address = this.addressForm.value;
    console.log(this.address);
    this.addressService.add(this.address as Address).subscribe(
      result => {
        this.toastr.success(result);
        console.log(result);
        // this.router.navigate(['address/add']);
      }
    );
    this.addressForm.reset();
  }

  onReset() {
    this.addressForm.reset();
  }
}
