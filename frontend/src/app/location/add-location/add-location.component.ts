import { Component, OnInit, OnDestroy, AfterViewInit } from '@angular/core';
import { Location } from 'src/app/shared/models/location.model';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { LocationService } from 'src/app/core/services/location.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-add-location',
  templateUrl: './add-location.component.html',
  styleUrls: ['./add-location.component.scss']
})
export class AddLocationComponent implements OnInit {

  location: Location;
  locationForm: FormGroup;
  constructor(
    private fb: FormBuilder,
    private router: Router,
    private locationService: LocationService,
    private toastr: ToastrService
  ) { this.createForm(); }

  ngOnInit() {
    this.location = {
      name: '',
      description: '',
      address: '',
      id: '',
      latitude: NaN,
      longitude: NaN
    };
  }

  createForm() {
    console.log('create form');
    this.locationForm = this.fb.group({
      name: ['', Validators.required],
      description: [''],
      address: ['', Validators.required],
      latitude: ['', Validators.required],
      longitude: ['', Validators.required]
    });
  }

  onLocationSubmit(e) {
    e.preventDefault();
    this.location = this.locationForm.value;
    console.log(this.location);
    this.locationService.add(this.location).subscribe(
      success => {
        this.toastr.success('Successfully added location');
        console.log(success);
        console.log(success.id);
        localStorage.setItem('selectedLocation', success.id);
        this.router.navigate(['location/update']);
      },
      error => {
        this.toastr.error(error);
      }
    );
  }

  onReset() {
    this.locationForm.reset();
  }
}
