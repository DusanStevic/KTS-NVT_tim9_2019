import { Component } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { Router, ActivatedRoute } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {

  title = 'frontend';
  public role: string;

  constructor(
    private toastr: ToastrService,
    private router: Router) {}

  checkRole() {
    const item = localStorage.getItem('user');

    if (!item) {
      this.role = undefined;
      return;
    }

    const jwt: JwtHelperService = new JwtHelperService();
    this.role = jwt.decodeToken(item).role;
    console.log(this.role);
  }

  showAddAddressForm() {
    this.router.navigate(['address/add']);
  }

  showUpdateAddressForm() {
    localStorage.setItem('selectedAddress', '1');
    this.router.navigate(['address/update']);
  }
}
