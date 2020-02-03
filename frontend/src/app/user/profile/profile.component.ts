import { Component, OnInit } from '@angular/core';
import { User } from '../../shared/models/user.model';
import { UserService } from 'src/app/core/services/user.service';
import { Location } from 'src/app/shared/models/location.model';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {
  user: User;
  userForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private userService: UserService,
    private toastr: ToastrService
  ) { 
    this.user = {
      id: NaN,
      username: '',
      firstName: '',
      lastName: '',
      email: '',
      phoneNumber: '',
      imageUrl: null,
      enabled: true,
      lastPasswordResetDate: null,
      firstTime: true,
  }
    this.createForm();
  }

  ngOnInit() {
    this.initUser();
  }

  initUser() {
    this.userService.whoAmI().subscribe(
      result => {
        console.log(result);
        this.user = result;
        if (result.imageUrl === null) {
          this.user.imageUrl = 'https://cdn.pixabay.com/photo/2016/08/08/09/17/avatar-1577909_960_720.png';
        }
        this.createForm();
      }
    );
  }

  createForm() {
    this.userForm = this.fb.group({
      id: [this.user.id, Validators.required],
      username: [this.user.username, [Validators.required, Validators.minLength(3)]],
      firstName: [this.user.firstName, Validators.required],
      lastName: [this.user.lastName, Validators.required],
      email: [this.user.email, [Validators.required, Validators.email ]],
      phoneNumber: [this.user.phoneNumber, Validators.required],
      imageUrl: [this.user.imageUrl],
      enabled: [this.user.enabled],
      lastPasswordResetDate: [this.user.lastPasswordResetDate]
    });
  }

  onUserSubmit(){
    this.toastr.info('Changes succesful', 'Asd');
  }

  onReset() {
    this.toastr.info('Changes reverted');
    this.initUser();
  }
}
