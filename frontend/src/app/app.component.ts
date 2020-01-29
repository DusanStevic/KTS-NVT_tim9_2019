import { Component } from '@angular/core';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {

  title = 'frontend';

  constructor(private toastr: ToastrService) {}

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
}
