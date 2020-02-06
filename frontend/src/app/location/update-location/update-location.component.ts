import { Component, OnInit } from '@angular/core';
import { Location } from 'src/app/shared/models/location.model';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { LocationService } from 'src/app/core/services/location.service';
import { ToastrService } from 'ngx-toastr';
import { HallDTO, Hall } from 'src/app/shared/models/hall.model';

@Component({
  selector: 'app-update-location',
  templateUrl: './update-location.component.html',
  styleUrls: ['./update-location.component.scss']
})
export class UpdateLocationComponent implements OnInit {

  location: Location;
  locationUpdForm: FormGroup;
  hallDto: HallDTO;
  hallForm: FormGroup;
  hallList: Hall[];
  constructor(
    private fb: FormBuilder,
    private router: Router,
    private locationService: LocationService,
    private toastr: ToastrService
  ) {
    this.location = {
      name: '',
      description: '',
      id: '',
      addressId: NaN
    };
    this.hallDto = {
      id: '',
      name: '',
      standingNr: NaN,
      sittingNr: NaN
    };
    this.createLocationForm();
    this.createHallForm();
  }

  ngOnInit() {
    console.log(localStorage.getItem('selectedLocation'));
    this.init();
  }

  createLocationForm() {
    console.log(localStorage.getItem('selectedLocation'));
    this.locationUpdForm = this.fb.group({
      name: [this.location.name, Validators.required],
      description: [this.location.description],
      addressId: ['']
    });
  }

  createHallForm() {
    this.hallForm = this.fb.group({
      name: ['', Validators.required],
      sittingNr: [''],
      standingNr: ['']
    });
  }

  init() {
    this.locationService.get(localStorage.getItem('selectedLocation')).subscribe(
      result => {
        console.log(result);
        this.location = result;
        console.log(result.halls);
        this.hallList = result.halls;
        console.log(this.location);
        console.log(this.location.id);
        console.log(this.hallList);
        this.createLocationForm();
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
        this.router.navigate(['location/all']);
      }
    );
  }

  onReset(e: Event) {
    console.log('upd on reset');
    // this.router.navigate(['location/update']);
    console.log(this.locationUpdForm.value);
    this.createLocationForm();
    console.log(this.locationUpdForm.value);
  }

  onHallSubmit(e: Event) {
    e.preventDefault();
    this.hallDto = this.hallForm.value;
    this.locationService.addHall(localStorage.getItem('selectedLocation'), this.hallDto).subscribe(
      result => {
        this.toastr.success('Successfully added hall');
        this.hallList.unshift(result);
        // console.log(this.hallList);
        this.hallList = this.hallList.filter(hall => 1 === 1);
      }
    );
  }
  onResetHall(e) {
    this.hallForm.reset();
  }

  onDelete(id: string) {
    console.log(id);
    this.locationService.deleteHall(id).subscribe(
      result => {
        this.toastr.success(result);
        console.log(result);
        console.log(result.body);
        this.hallList = this.hallList.filter(hall => hall.id !== id);
        console.log(this.hallList);
      }
    );
  }
}
