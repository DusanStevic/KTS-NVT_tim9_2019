import { Component, OnInit } from '@angular/core';

import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { JwtHelperService } from '@auth0/angular-jwt';

@Component({
  selector: 'app-navbar-registered-user',
  templateUrl: './navbar-registered-user.component.html',
  styleUrls: ['./navbar-registered-user.component.scss']
})
export class NavbarRegisteredUserComponent implements OnInit {

  constructor(private router: Router, private toastr: ToastrService) {
  }

  ngOnInit() {
  }

  logOut(): void {
    localStorage.removeItem('user');
    this.toastr.success('Succesful logout!');
    this.router.navigate(['']);
    location.reload();
  }

}
