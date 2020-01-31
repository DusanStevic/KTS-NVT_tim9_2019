import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToastrModule } from 'ngx-toastr';
import { MaterialModule } from './material/material.module';
import { AddAddressComponent } from './address/add-address/add-address.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AuthenticationModule } from './authentication/authentication.module';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { UpdateAddressComponent } from './address/update-address/update-address.component';
import { AddressFormComponent } from './address/address-form/address-form.component';


@NgModule({
  declarations: [
    AppComponent,
    AddAddressComponent,
    UpdateAddressComponent,
    AddressFormComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    ToastrModule.forRoot({
      timeOut: 3000,
      closeButton: true,
      positionClass: 'toast-top-right',
      preventDuplicates: true
    }),
    AuthenticationModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
