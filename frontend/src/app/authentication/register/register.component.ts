import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { AuthenticationService } from 'src/app/core/services/authentication.service';
import { Registration } from 'src/app/shared/models/registration.model';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {
  registrationForm: FormGroup;
  registration: Registration;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private authenticationService: AuthenticationService,
    private toastr: ToastrService) {  }

  ngOnInit() {
    this.createForm();
  }

  createForm(): void {
    this.registrationForm = this.fb.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      username: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });
  }

  submit() {
    this.registration = this.registrationForm.value;


    this.authenticationService.register(this.registration as Registration).subscribe(
      result => {
        this.router.navigate(['events']);
        this.toastr.success('We have sent an email with a confirmation link to your email address.' +
        'In order to complete the sign-up process, please click the confirmation link!');
      },
      error => {
        this.registrationForm.reset();
        this.router.navigate(['events']);
        this.toastr.error('There was an error with your registration. Please try registering again!');
      }
    );
  }


}
