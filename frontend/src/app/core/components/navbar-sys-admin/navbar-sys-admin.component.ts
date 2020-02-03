import { Component, OnInit } from '@angular/core';

import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { JwtHelperService } from '@auth0/angular-jwt';

@Component({
  selector: 'app-navbar-sys-admin',
  templateUrl: './navbar-sys-admin.component.html',
  styleUrls: ['./navbar-sys-admin.component.scss']
})
export class NavbarSysAdminComponent implements OnInit {

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
