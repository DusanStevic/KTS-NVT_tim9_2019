import { Component, OnInit } from '@angular/core';

import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { JwtHelperService } from '@auth0/angular-jwt';

@Component({
  selector: 'app-navbar-admin',
  templateUrl: './navbar-admin.component.html',
  styleUrls: ['./navbar-admin.component.scss']
})
export class NavbarAdminComponent implements OnInit {

  constructor(private router: Router, private toastr: ToastrService) {
  }

  ngOnInit() {
  }
  
  logOut(): void {
    localStorage.removeItem('user');
    this.toastr.success("Succesful logout!");
    this.router.navigate(['']);
    location.reload();
  }

}
