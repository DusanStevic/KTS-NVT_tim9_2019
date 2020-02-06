import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { ReservationDetailed } from '../../shared/models/reservationDetailed.model';
import { ReservationService } from 'src/app/core/services/reservation.service';

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
    private route: ActivatedRoute
  ) {
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
                description: this.product.description,
                amount: {
                  currency_code: 'USD',
                  value: this.product.price
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

  getReservation() {
    const id = +this.route.snapshot.paramMap.get('id');
    this.reservationService.getReservation(id).subscribe(
      success => {
        this.reservation = success;
        console.log(success);
        this.paidFor = this.reservation.purchased;
        if (!this.paidFor) {
          this.initPaypal();
        }
      },
      error => {
        this.toastr.error('You can only access your reservation!');
        this.router.navigate(['/myReservations']);
      }
    );
  }

}
