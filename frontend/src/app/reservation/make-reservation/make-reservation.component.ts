import { Component, OnInit } from '@angular/core';
import { Hall, SittingSector } from 'src/app/shared/models/hall.model';
import { Event } from 'src/app/shared/models/event.model';
import { EventSectorDTO } from 'src/app/shared/models/create-event.model';
import { EventSector } from 'src/app/shared/models/event-sector.model';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { LocationService } from 'src/app/core/services/location.service';
import { EventService } from 'src/app/core/services/event.service';
import { ToastrService } from 'ngx-toastr';
import { ReservationDTO, SittingTicketDTO, StandingTicketDTO } from 'src/app/shared/models/reservation.model';
import { ReservationService } from 'src/app/core/services/reservation.service';

@Component({
  selector: 'app-make-reservation',
  templateUrl: './make-reservation.component.html',
  styleUrls: ['./make-reservation.component.scss']
})
export class MakeReservationComponent implements OnInit {

  event: Event;
  eventSectors: EventSector[];
  reservation: ReservationDTO;
  standForm: FormGroup;
  constructor(
    private fb: FormBuilder,
    private router: Router,
    private reservationService: ReservationService,
    private eventService: EventService,
    private toastr: ToastrService
  ) {
    this.reservation = {
      sittingTickets: [],
      standingTickets: [],
      purchased: false,
      eventDayId: NaN
    };
    this.eventSectors = [];
    this.standForm = this.fb.group({
      standSector: [''],
      numOfStandingTickets: ['']
    });
  }

  ngOnInit() {
    this.eventService.get(localStorage.getItem('selectedEvent')).subscribe(
      result => {
        console.log(result);
        this.event = result;
        this.eventSectors = result.eventSectors;
      }
    );
  }

  processReservation(obj) {
    console.log(obj);
    this.reservation.eventDayId = +localStorage.getItem('selectedEventDay');
    this.reservation.purchased = false;
    obj.seatObjects.forEach( x => {
      const sitTicket: SittingTicketDTO = {
        type: 'sittingTicketDTO',
        eventSectorId: x.sectorId,
        row: x.row,
        col: x.col
      };
      this.reservation.sittingTickets.push(sitTicket);
    });
    if (this.standForm.get('standSector').value !== '') {
      const standTicket: StandingTicketDTO = {
        type: 'standingTicketDTO',
        eventSectorId: +this.standForm.get('standSector').value,
        numOfStandingTickets: this.standForm.get('numOfStandingTickets').value
      };
      this.reservation.standingTickets.push(standTicket);
    }
    this.reservationService.makeReservation(this.reservation).subscribe(
      success => {
        this.toastr.success('Successfully reserved event day');
        console.log(success);
      },
      error => {
        this.toastr.error(error.error);
        console.log(error);
      }
    );
    console.log(this.reservation);
  }
}
