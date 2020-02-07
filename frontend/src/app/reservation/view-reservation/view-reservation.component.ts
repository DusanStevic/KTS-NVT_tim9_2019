import { Component, OnInit, ViewChild, ElementRef, Inject } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { ReservationDetailed } from '../../shared/models/reservationDetailed.model';
import { ReservationService } from 'src/app/core/services/reservation.service';
import { MatDialog, MAT_DIALOG_DATA} from '@angular/material/dialog';

declare var paypal;

@Component({
  selector: 'app-view-reservation',
  templateUrl: './view-reservation.component.html',
  styleUrls: ['./view-reservation.component.scss']
})
export class ViewReservationComponent implements OnInit {

  @ViewChild('paypal', { static: true }) paypalElement: ElementRef;

  reservation: ReservationDetailed;
  paidFor = true;
  canCancel = true;
  renderedPayPal = false;

  product = {
    price: 60.60,
    description: 'Testitem',
    // tslint:disable-next-line: max-line-length
    img: 'https://www.awashfalllodge.com/img/0176/089.png'
  };

  constructor(
    private reservationService: ReservationService,
    private router: Router,
    private toastr: ToastrService,
    private route: ActivatedRoute,
    public dialog: MatDialog
  ) {
    this.reservation = {
      id: 0,
      eventName: 'event',
      purchased: false,
      tickets: [
        {
          dateED: new Date(),
          hallName: '',
          hasSeat: true,
          locationName: '',
          nameED: '',
          nameEvent: '',
          numCol: 0,
          numRow: 0,
          price: 0,
          reservationID: 0,
          sectorName: ''
        }
      ],
      fullPrice: 0,
      username: '',
      reservationDate: new Date()
    };
    this.getReservation();
  }

  ngOnInit() {
  }


  initPaypal() {
    if (this.renderedPayPal) {
      return;
    }
    paypal
      .Buttons({
        createOrder: (data, actions) => {
          return actions.order.create({
            purchase_units: [
              {
                description: this.reservation.eventName,
                amount: {
                  currency_code: 'USD',
                  value: this.reservation.fullPrice
                }
              }
            ]
          });
        },
        onApprove: async (data, actions) => {
          const order = await actions.order.capture();
          this.reservationService.puchaseReservation(this.reservation.id).subscribe(
            succes => {
              this.paidFor = true;
              this.router.navigate(['/myReservations']);
              this.toastr.success('You succesfully bought the tickets.');
            }, error => {
              this.toastr.error('Error when trying to pay for reservation!');
              console.log(error);
            }
          );

          console.log(order);
        },
        onError: err => {
          console.log(err);
          this.toastr.error('Error when trying to pay for reservation!');
        }
      })
      .render(this.paypalElement.nativeElement);
    this.renderedPayPal = true;
  }

  openDialog() {
    const dialogRef = this.dialog.open(ConfirmCancelReservationDialog);

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.cancelReservation();
      }
    });
  }

  cancelReservation() {
    this.reservationService.cancelReservation(this.reservation.id).subscribe(
      success => {
        this.paidFor = false;
        this.router.navigate(['/myReservations']);
        this.toastr.success('You succesfully canceled the reservation.');
      }, error => {
        this.toastr.error('Error when trying to cancel reservation!');
        console.log(error);
      }
    );
  }

  getReservation() {
    const id = +this.route.snapshot.paramMap.get('id');
    this.reservationService.getReservation(id).subscribe(
      success => {
        this.reservation = success;
        console.log(success);
        this.paidFor = this.reservation.purchased;
        if (!this.paidFor) {
          this.initPaypal();
        } else {
          this.canCancel = false;
        }
      },
      error => {
        this.toastr.error('You can only access your reservation!');
        this.router.navigate(['/myReservations']);
      }
    );
  }

}

@Component({
  selector: 'app-confirm-cancel-dialog',
  templateUrl: 'confirm-cancel-reservation-dialog.html',
})
// tslint:disable-next-line: component-class-suffix
export class ConfirmCancelReservationDialog {}
