import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { JwtHelperService } from '@auth0/angular-jwt';
import { AuthenticationService } from 'src/app/core/services/authentication.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  form: FormGroup;

  constructor(
    private fb: FormBuilder,
    private authenticationService: AuthenticationService,
    private router: Router,
    private toastr: ToastrService
  ) {
    this.form = this.fb.group({
      username : [null, Validators.required],
      password: [null, Validators.required]
    });
  }

  ngOnInit() {
    if (this.authenticationService.isLoggedIn()) {
      this.router.navigate(['events']);
    }
  }

  submit() {
    const auth: any = {};
    const jwt: JwtHelperService = new JwtHelperService();
    auth.username = this.form.value.username;
    auth.password = this.form.value.password;

    this.authenticationService.login(auth).subscribe(
      result => {
        this.toastr.success('Successful login!');
        localStorage.setItem('user', JSON.stringify(result));
        this.router.navigate(['events']);
      },
      error => {
        this.toastr.error('Incorrect username or password!');
      }
    );
  }


}
