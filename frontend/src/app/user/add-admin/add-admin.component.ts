import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Registration } from 'src/app/shared/models/registration.model';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { UserService } from 'src/app/core/services/user.service';

@Component({
  selector: 'app-add-admin',
  templateUrl: './add-admin.component.html',
  styleUrls: ['./add-admin.component.scss']
})
export class AddAdminComponent implements OnInit {
  addAdminForm: FormGroup;
  addAdmin: Registration;
  constructor(
    private fb: FormBuilder,
    private router: Router,
    private userService: UserService,
    private toastr: ToastrService) {  }

  ngOnInit() {
    this.createForm();
  }

  createForm(): void {
    this.addAdminForm = this.fb.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      username: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });
  }

  submit() {
    this.addAdmin = this.addAdminForm.value;


    this.userService.addAdmin(this.addAdmin as Registration).subscribe(
      result => {
        this.router.navigate(['events']);
        this.toastr.success('You have successfully added admin!');
      },
      error => {
        this.addAdminForm.reset();
        this.router.navigate(['events']);
        this.toastr.error('Add Admin Failed There was an error adding the administrator!');
      }
    );
  }

}
