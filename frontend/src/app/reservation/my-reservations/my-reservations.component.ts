import { Component, OnInit, ViewChild, ElementRef, Renderer2 } from '@angular/core';
import { ReservationDetailed } from '../../shared/models/reservationDetailed.model';
import { ReservationService } from 'src/app/core/services/reservation.service';
import { FormGroup, FormBuilder, Validators, FormControl, FormGroupDirective, NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { ErrorStateMatcher} from '@angular/material/core';

import {MatPaginator} from '@angular/material/paginator';
import {MatTableDataSource} from '@angular/material/table';
import {MatSort} from '@angular/material/sort';

@Component({
  selector: 'app-my-reservations',
  templateUrl: './my-reservations.component.html',
  styleUrls: ['./my-reservations.component.scss']
})
export class MyReservationsComponent implements OnInit {
  reservationList: ReservationDetailed[];
  displayedColumns: string[] = ['eventName', 'reservationDate', 'ticketsNumber', 'fullPrice', 'details'];
  dataSource: MatTableDataSource<ReservationDetailed>;

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: true}) sort: MatSort;

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
        this.dataSource = new MatTableDataSource<ReservationDetailed>(this.reservationList);
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
      }
    );
  }

  pay(id: number) {
    this.toastr.info('I paid for ' + id);
  }

  cancel(id: number) {
    this.toastr.info('I canceled ' + id);
  }

}
