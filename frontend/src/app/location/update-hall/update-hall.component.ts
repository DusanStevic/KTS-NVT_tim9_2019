import { Component, OnInit } from '@angular/core';
import { HallUpdDTO, HallDTO, SittingSector, StandingSector } from 'src/app/shared/models/hall.model';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { LocationService } from 'src/app/core/services/location.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-update-hall',
  templateUrl: './update-hall.component.html',
  styleUrls: ['./update-hall.component.scss']
})
export class UpdateHallComponent implements OnInit {

  hall: HallDTO;
  hallUpdForm: FormGroup;
  sittingSector: SittingSector;
  standingSector: StandingSector;
  sectorForm: FormGroup;
  constructor(
    private fb: FormBuilder,
    private router: Router,
    private locationService: LocationService,
    private toastr: ToastrService
  ) {
    this.hall = {
      name: '',
      sittingNr: NaN,
      standingNr: NaN,
      id: ''
    };
    this.sittingSector = {
      id: '',
      name: '',
      numRows: NaN,
      numCols: NaN,
      type: 'sitting'
    };
    this.standingSector = {
      id: '',
      name: '',
      capacity: NaN,
      type: 'standing'
    };
    this.createHallForm();
    this.createSectorForm();
  }

  ngOnInit() {
    console.log('init');
    this.init();
  }

  createHallForm() {
    console.log(this.hall.name);
    this.hallUpdForm = this.fb.group({
      name: [this.hall.name, Validators.required],
      sittingNr: [{value: '', disabled: true}],
      standingNr: [{value: '', disabled: true}]
    });
  }

  createSectorForm() {
    this.sectorForm = this.fb.group({
      name: ['', Validators.required],
      numRows: [''],
      numCols: [''],
      capacity: [''],
      type: ['']
    });
  }
  init() {
    this.locationService.getHall(localStorage.getItem('selectedHall')).subscribe(
      result => {
        console.log(result);
        this.hall = result;
        console.log(this.hall);
        this.createHallForm();
      }
    );
  }

  onHallSubmit(e: Event) {
    e.preventDefault();
    this.hall = this.hallUpdForm.value;
    this.locationService.updateHall(localStorage.getItem('selectedHall'), this.hall).subscribe(
      result => {
        this.toastr.success('Successfully updated hall');
        // this.hallList.push(result);
        // console.log(this.hallList);
        // this.hallList = this.hallList.filter(hall => 1 === 1);
      }
    );
  }
  onResetHall(e) {
    this.createHallForm();
  }

  onSectorSubmit(e: Event) {
    e.preventDefault();
    console.log(this.sectorForm.value.type);
    if (this.sectorForm.value.type === 'sittingDTO') {
      this.sittingSector = this.sectorForm.value;
      console.log(this.sittingSector);
      this.locationService.addSector(localStorage.getItem('selectedHall'), this.sittingSector).subscribe(
        result => {
          console.log(result);
          this.toastr.success('Successfully added sitting sector');
          // this.hallList.unshift(result);
          // this.hallList = this.hallList.filter(hall => 1 === 1);
        }
      );
    } else if (this.sectorForm.value.type === 'standingDTO') {
      this.standingSector = this.sectorForm.value;
      this.locationService.addSector(localStorage.getItem('selectedHall'), this.standingSector).subscribe(
        result => {
          console.log(result);
          this.toastr.success('Successfully added standing sector');
          // this.hallList.unshift(result);
          // this.hallList = this.hallList.filter(hall => 1 === 1);
        }
      );
    }
  }
  onResetSector(e) {
    this.sectorForm.reset();
  }

  goBack() {
    this.router.navigate(['location/update']);
  }
}
