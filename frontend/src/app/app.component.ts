import { Component } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {

  title = 'frontend';

  constructor(
    private toastr: ToastrService,
    private router: Router) {}

  showSuccess() {
    this.toastr.success('Hello world!', 'Success');
  }

  showError() {
    this.toastr.error('Hello world!', 'Error');
  }

  showWarning() {
    this.toastr.warning('Hello world!', 'Warning');
  }

  showInfo() {
    this.toastr.info('Hello world!', 'Info');
  }

  showAddAddressForm() {
    this.router.navigate(['address/add']);
  }

  showUpdateAddressForm() {
    localStorage.setItem('selectedAddress', '1');
    this.router.navigate(['address/update']);
  }
}
