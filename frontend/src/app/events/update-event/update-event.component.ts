import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { LocationService } from 'src/app/core/services/location.service';
import { EventUpdateDTO, EventSectorDTO } from 'src/app/shared/models/create-event.model';
import { EventService } from 'src/app/core/services/event.service';
import { Sector } from 'src/app/shared/models/hall.model';

@Component({
  selector: 'app-update-event',
  templateUrl: './update-event.component.html',
  styleUrls: ['./update-event.component.scss']
})
export class UpdateEventComponent implements OnInit {

  event: EventUpdateDTO;
  eventUpdForm: FormGroup;
  eventSectorDTO: EventSectorDTO;
  eventSectorForm: FormGroup;
  sectorList: Sector[];
  constructor(
    private fb: FormBuilder,
    private router: Router,
    private locationService: LocationService,
    private eventService: EventService,
    private toastr: ToastrService
  ) {
    this.event = {
      name: '',
      description: ''
    };
    this.eventSectorDTO = {
      price: NaN,
      sectorId: ''
    };
    this.createEventForm();
    this.createEventSectorForm('');
  }

  ngOnInit() {
    this.init();
  }

  createEventForm() {
    // localStorage.setItem('selectedEvent', '1');
    console.log(localStorage.getItem('selectedEvent'));
    this.eventUpdForm = this.fb.group({
      name: [this.event.name, Validators.required],
      description: [this.event.description]
    });
  }

  createEventSectorForm(name: string) {
    this.eventSectorForm = this.fb.group({
      name: [{value: name, disabled: true}],
      price: ['', Validators.required]
    });
  }
  init() {
    this.eventService.get(localStorage.getItem('selectedEvent')).subscribe(
      result => {
        console.log(result);
        this.event = result;
        this.sectorList = result.hall.sectors;
        result.eventSectors.forEach(es => {
          this.sectorList = this.sectorList.filter(sector => sector.id !== es.sector.id);
        });
        this.createEventForm();
      }
    );
  }

  onEventSubmit(e) {
    e.preventDefault();
    console.log('update ev');
    this.event = this.eventUpdForm.value;
    // this.address.id = 1;
    console.log(this.event);
    this.eventService.update(this.event, localStorage.getItem('selectedEvent')).subscribe(
      result => {
        this.toastr.success('Successfully updated event');
        console.log(result);
        this.router.navigate(['events']);
      }
    );
  }

  onReset(e: Event) {
    console.log('upd on reset');
    // this.router.navigate(['location/update']);
    console.log(this.eventUpdForm.value);
    this.createEventForm();
    console.log(this.eventUpdForm.value);
  }

  onAddEventSector(item: any) {
    console.log(item.sectorId);
    console.log(item.sectorName);
    this.eventSectorDTO.sectorId = item.sectorId;
    this.createEventSectorForm(item.sectorName);
  }

  onEventSectorSubmit(e: Event) {
    console.log('es submit');
    this.eventSectorDTO.price = this.eventSectorForm.get('price').value;
    console.log(this.eventSectorDTO);
    this.eventService.addEventSector(localStorage.getItem('selectedEvent'), this.eventSectorDTO).subscribe (
      success => {
        this.toastr.success('Successfully added event sector');
        this.sectorList = this.sectorList.filter(sector => sector.id !== success.sector.id);
        this.createEventSectorForm('');
      },
      error => {
        this.toastr.error(error.error);
        console.log(error.error);
      }
    );
  }
}
