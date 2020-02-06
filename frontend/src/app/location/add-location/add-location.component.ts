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
      addressId: 1,
      id: ''
    };
  }

  createForm() {
    console.log('create form');
    this.locationForm = this.fb.group({
      name: ['', Validators.required],
      description: [''],
      addressId: [1, Validators.required]
    });
  }

  onLocationSubmit(e) {
    e.preventDefault();
    this.location = this.locationForm.value;
    console.log(this.location);
    this.locationService.add(this.location).subscribe(
      result => {
        this.toastr.success('Successfully added location');
        console.log(result);
        console.log(result.id);
        localStorage.setItem('selectedLocation', result.id);
        this.router.navigate(['location/details']);
      }
    );
  }

  onReset() {
    this.locationForm.reset();
  }
}
