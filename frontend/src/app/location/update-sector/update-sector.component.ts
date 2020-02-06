import { Component, OnInit } from '@angular/core';
import { Sector, SittingSector, StandingSector } from 'src/app/shared/models/hall.model';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { LocationService } from 'src/app/core/services/location.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-update-sector',
  templateUrl: './update-sector.component.html',
  styleUrls: ['./update-sector.component.scss']
})
export class UpdateSectorComponent implements OnInit {

  sittingSector: SittingSector;
  standingSector: StandingSector;
  sectorUpdForm: FormGroup;
  sectorType: string;
  constructor(
    private fb: FormBuilder,
    private router: Router,
    private locationService: LocationService,
    private toastr: ToastrService
  ) {
    this.sectorType = '';
    this.sittingSector = {
      id: '',
      name: '',
      type: '',
      numCols: NaN,
      numRows: NaN
    };
    this.standingSector = {
      id: '',
      name: '',
      type: '',
      capacity: NaN
    };
    this.createForm('');
   }

  ngOnInit() {
    this.initSector();
  }

  createForm(type: string) {
    if (type === 'sittingDTO') {
      this.sectorUpdForm = this.fb.group({
        name: [this.sittingSector.name, Validators.required],
        type: [{value: type, disabled: true}],
        numRows: [this.sittingSector.numRows, Validators.required],
        numCols: [this.sittingSector.numCols, Validators.required],
        capacity: [{value: '', disabled: true}]
      });
    } else if (type === 'standingDTO') {
      this.sectorUpdForm = this.fb.group({
        name: [this.standingSector.name, Validators.required],
        type: [{value: type, disabled: true}],
        numRows: [{value: '', disabled: true}],
        numCols: [{value: '', disabled: true}],
        capacity: [this.standingSector.capacity, Validators.required]
      });
    } else {
      this.sectorUpdForm = this.fb.group({
        name: [''],
        type: [''],
        numRows: [''],
        numCols: [''],
        capacity: ['']
      });
    }
    this.sectorUpdForm.get('type').disable();
  }

  initSector() {
    this.locationService.getSector(localStorage.getItem('selectedSector')).subscribe (
      success => {
        if (success.type === 'sitting') {
          this.sittingSector = success;
        } else if (success.type === 'standing') {
          this.standingSector = success;
        }
        this.sectorType = success.type + 'DTO';
        this.createForm(success.type + 'DTO');
      }
    );
  }

  onSectorSubmit(e: Event) {
    e.preventDefault();
    console.log(this.sectorUpdForm.value.type);
    if (this.sectorType === 'sittingDTO') {
      this.sittingSector = this.sectorUpdForm.value;
      console.log(this.sittingSector);
      this.sittingSector.type = this.sectorType;
      this.locationService.updateSector(localStorage.getItem('selectedSector'), this.sittingSector).subscribe(
        success => {
          console.log(success);
          this.toastr.success('Successfully updated sitting sector');
          this.router.navigate(['hall/update']);
        },
        error => {
          this.toastr.error(error);
        }
      );
    } else if (this.sectorType === 'standingDTO') {
      this.standingSector = this.sectorUpdForm.value;
      this.standingSector.type = this.sectorType;
      this.locationService.updateSector(localStorage.getItem('selectedSector'), this.standingSector).subscribe(
        success => {
          console.log(success);
          this.toastr.success('Successfully updated standing sector');
          this.router.navigate(['hall/update']);
        },
        error => {
          this.toastr.error(error);
        }
      );
    } else {
      console.log('nista');
    }
  }

  onReset(e: any) {
    console.log(this.sectorUpdForm.value.type);
    this.createForm(this.sectorType);
  }

  goBack() {
    this.router.navigate(['hall/update']);
  }
  /*disable(type: string) {
    console.log('disablee');
    console.log(type);
    if (type === 'sittingDTO') {
      this.sectorUpdForm.get('capacity').disable();
      this.sectorUpdForm.get('numRows').enable();
      this.sectorUpdForm.get('numCols').enable();
    } else if (type === 'standingDTO') {
      this.sectorUpdForm.get('capacity').enable();
      this.sectorUpdForm.get('numRows').disable();
      this.sectorUpdForm.get('numCols').disable();
    }
  }*/
}
