import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ChangePasswordComponent } from './change-password/change-password.component';
import { ProfileComponent } from './profile/profile.component';
import { AppRoutingModule } from 'src/app/app-routing.module';
import { MaterialModule } from '../material/material.module';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { AddAdminComponent } from './add-admin/add-admin.component';



@NgModule({
  declarations: [ChangePasswordComponent, ProfileComponent, AddAdminComponent],
  imports: [
    CommonModule,
    AppRoutingModule,
    MaterialModule,
    ReactiveFormsModule,
    FormsModule
  ],
  exports: [
    ChangePasswordComponent,
    ProfileComponent,
    AddAdminComponent
  ],
})
export class UserModule { }
