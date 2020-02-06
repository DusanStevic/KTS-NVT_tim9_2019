import { Component, OnInit, ViewChild, ElementRef, Renderer2 } from '@angular/core';
import { User } from '../../shared/models/user.model';
import { UserService } from 'src/app/core/services/user.service';
import { FormGroup, FormBuilder, Validators, FormControl, FormGroupDirective, NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { ErrorStateMatcher} from '@angular/material/core';
import { FileUploadService } from 'src/app/core/services/file-upload.service';

/** Error when invalid control is dirty, touched, or submitted. */
export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {
  selectedFile: File = null;
  user: User;
  userForm: FormGroup;

  loading = false;
  submitted = false;
  oldUsername: string;

  matcher = new MyErrorStateMatcher();

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private userService: UserService,
    private toastr: ToastrService,
    private renderer: Renderer2,
    private fileUploadService: FileUploadService
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
  };
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
        this.oldUsername = result.username;
        if (!result.imageUrl) {
          this.user.imageUrl = 'https://cdn.pixabay.com/photo/2016/08/08/09/17/avatar-1577909_960_720.png';
        }
        this.createForm();
      },
      error => {
        this.toastr.error('Please log in again to resolve errors!', 'An error accured');
      }
    );
  }

  onChange(event: any) {
    event.preventDefault();
    this.user.imageUrl = event.target.value;
  }

  createForm() {
    this.userForm = this.fb.group({
      id: [this.user.id, Validators.required],
      username: [this.user.username, [Validators.required, Validators.minLength(3)]],
      firstName: [this.user.firstName, Validators.required],
      lastName: [this.user.lastName, Validators.required],
      email: [this.user.email, [Validators.required, Validators.email ]],
      phoneNumber: [this.user.phoneNumber, Validators.required]
    });
  }

  get f() { return this.userForm.controls; }

  onSubmit() {
    this.submitted = true;

    if (this.userForm.invalid) {
      this.toastr.warning('Data is not valid');
      return;
    }

    let isUsernameChanged = true;

    if (this.oldUsername === this.userForm.value.username) {
      isUsernameChanged = false;
    }

    this.loading = true;
    const updUser: User = new User();
    updUser.id = this.user.id;
    updUser.username = this.userForm.value.username;
    updUser.firstName = this.userForm.value.firstName;
    updUser.lastName = this.userForm.value.lastName;
    updUser.email = this.userForm.value.email;
    updUser.phoneNumber = this.userForm.value.phoneNumber;
    updUser.imageUrl = this.user.imageUrl;
    updUser.enabled = this.user.enabled;
    updUser.lastPasswordResetDate = this.user.lastPasswordResetDate;
    updUser.firstTime = this.user.firstTime;
    console.log(updUser);
    this.userService.updateUser(updUser).subscribe (
      success => {
        this.loading = false;
        this.toastr.success('Profile updated succesfully!', 'Succes');
        if (isUsernameChanged) {
          this.toastr.success('Log in with new username!', 'Username changed');
          localStorage.removeItem('user');
          this.router.navigate(['/login']);
        }
      },
      error => {
        // dodaj warning ako same email ili ako same username
        this.toastr.error('Username or email is taken!');
        this.userForm.value.email = this.user.email;
        this.userForm.value.username = this.user.username;
        this.loading = false;
      }
    );
  }

  onFileSelected(event): void {
    this.selectedFile = event.target.files[0] as File;
  }

  onUpload() {
    const formData = new FormData();
    formData.append('file', this.selectedFile, this.selectedFile.name);
    this.fileUploadService.updateProfileImage(formData).subscribe(response => {
      console.log(response);
      this.toastr.success('You successfully updated profile image.');
      this.user.imageUrl = response.imageUrl;
    }, error => {
      this.toastr.error('There was an error while updating profile image.');
    });

  }

  reset(event: any) {
    event.preventDefault();
    this.createForm();
  }
}
