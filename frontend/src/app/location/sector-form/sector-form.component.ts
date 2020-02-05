import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { SittingSector, Sector } from 'src/app/shared/models/hall.model';
import { FormGroup } from '@angular/forms';

@Component({
  selector: 'app-sector-form',
  templateUrl: './sector-form.component.html',
  styleUrls: ['./sector-form.component.scss']
})
export class SectorFormComponent implements OnInit {

  @Input() sector: Sector;
  @Input() sectorForm: FormGroup;
  @Output() resetSectorClicked: EventEmitter<any>;
  constructor() {
    this.resetSectorClicked = new EventEmitter();
   }

  ngOnInit() {
  }

  onSectorSubmit() {
    console.log('submit');
  }

  onResetSector() {
    console.log('reset');
    this.resetSectorClicked.emit();
  }

  disable(type: string) {
    console.log('disablee');
    console.log(type);
    if (type === 'sittingDTO') {
      this.sectorForm.get('capacity').disable();
      this.sectorForm.get('numRows').enable();
      this.sectorForm.get('numCols').enable();
    } else if (type === 'standingDTO') {
      this.sectorForm.get('capacity').enable();
      this.sectorForm.get('numRows').disable();
      this.sectorForm.get('numCols').disable();
    }
  }
}
