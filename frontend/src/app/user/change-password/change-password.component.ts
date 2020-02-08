import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MustMatch } from '../../core/validators/must-match.validators';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { PasswordChanger } from 'src/app/shared/models/passwordChanger.model';
import { AuthenticationService } from '../../core/services/authentication.service';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.scss']
})
export class ChangePasswordComponent implements OnInit {

  changePasswordForm: FormGroup;
  submitted = false;
  pwChanger: PasswordChanger;
  
  errorMessage = '';

  constructor(
      private formBuilder: FormBuilder,
      private router: Router,
      private toastr: ToastrService,
      private authService: AuthenticationService
      ) {
        this.createForm();
      }

  ngOnInit() {
  }

  createForm() {
    this.changePasswordForm = this.formBuilder.group({
        oldPassword: ['', Validators.required],
        newPassword: ['', [Validators.required, Validators.minLength(3)]],
        confirmPassword: ['', Validators.required]
    }, {
        validator: MustMatch('newPassword', 'confirmPassword')
    });
  }

  // convenience getter for easy access to form fields
  get f() { return this.changePasswordForm.controls; }

  onSubmit() {
      this.submitted = true;

      // stop here if form is invalid
      if (this.changePasswordForm.invalid) {
          this.toastr.warning('Please follow the instructions', 'Warning!');
          this.errorMessage = 'Please follow the instructions';
          return;
      }

      if (this.changePasswordForm.value.newPassword === this.changePasswordForm.value.oldPassword) {
        this.toastr.warning('New password is identical to old password!', 'Warning!');
        this.errorMessage = 'New password is identical to old password!';
        return;
      }
      this.pwChanger = new PasswordChanger();
      this.pwChanger.oldPassword = this.changePasswordForm.value.oldPassword;
      this.pwChanger.newPassword = this.changePasswordForm.value.newPassword;

      this.authService.changePassword(this.pwChanger).subscribe(
          success => {
              this.toastr.success('Log in with new password!', 'Password changed');
              this.changePasswordForm.reset();
              localStorage.removeItem('user');
              this.router.navigate(['/login']);
          },
          error => {
            this.toastr.error('The entered old password is not correct!');
            this.errorMessage = 'The entered old password is not correct!';
            this.changePasswordForm.patchValue( { oldPassword : ''});
          }
      );
  }
}
