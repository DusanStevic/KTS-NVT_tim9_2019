import { Component, OnInit } from '@angular/core';
import { Location } from 'src/app/shared/models/location.model';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { LocationService } from 'src/app/core/services/location.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-update-location',
  templateUrl: './update-location.component.html',
  styleUrls: ['./update-location.component.scss']
})
export class UpdateLocationComponent implements OnInit {

  location: Location;
  locationUpdForm: FormGroup;
  constructor(
    private fb: FormBuilder,
    private router: Router,
    private locationService: LocationService,
    private toastr: ToastrService
  ) {
    this.location = {
      name: '',
      description: '',
      addressId: NaN,
      id: ''
    };
    this.createForm();
  }

  ngOnInit() {
    console.log(localStorage.getItem('selectedLocation'));
    this.initLocation();
  }

  createForm() {
    console.log(localStorage.getItem('selectedLocation'));
    this.locationUpdForm = this.fb.group({
      name: [this.location.name, Validators.required],
      description: [this.location.description],
      addressId: [this.location.addressId]
    });
  }

  initLocation() {
    this.locationService.get(localStorage.getItem('selectedLocation')).subscribe(
      result => {
        console.log(result);
        this.location = result;
        console.log(this.location);
        console.log(this.location.id);
        this.createForm();
      }
    );
  }

  onLocationSubmit(e) {
    e.preventDefault();
    console.log('update loc');
    this.location = this.locationUpdForm.value;
    // this.address.id = 1;
    console.log(this.location.id);
    console.log(this.location);
    this.locationService.update(this.location as Location, localStorage.getItem('selectedLocation')).subscribe(
      result => {
        this.toastr.success('Successfully updated location');
        console.log(result);
        // this.router.navigate(['location/all']);
      }
    );
  }

  onReset(e: Event) {
    console.log('upd on reset');
    // this.router.navigate(['location/update']);
    console.log(this.locationUpdForm.value);
    this.createForm();
    console.log(this.locationUpdForm.value);
  }
}
