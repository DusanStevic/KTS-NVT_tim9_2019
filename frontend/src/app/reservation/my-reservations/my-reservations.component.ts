import { Component, OnInit, ViewChild, ElementRef, Renderer2 } from '@angular/core';
import { ReservationDetailed } from '../../shared/models/reservationDetailed.model';
import { ReservationService } from 'src/app/core/services/reservation.service';
import { FormGroup, FormBuilder, Validators, FormControl, FormGroupDirective, NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { ErrorStateMatcher} from '@angular/material/core';

@Component({
  selector: 'app-my-reservations',
  templateUrl: './my-reservations.component.html',
  styleUrls: ['./my-reservations.component.scss']
})
export class MyReservationsComponent implements OnInit {
  reservationList: ReservationDetailed[];

  constructor(
    private reservationService: ReservationService,
    private router: Router,
    private toastr: ToastrService
  ) { }

  ngOnInit() {
    this.initReservations();
  }

  initReservations() {
    this.reservationService.myReservations().subscribe(
      success => {
        this.reservationList = success;
        console.log(success);
      }
    );
  }

}
