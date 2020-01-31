import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToastrModule } from 'ngx-toastr';
import { MaterialModule } from './material/material.module';
import { AddAddressComponent } from './address/add-address/add-address.component';
import { AuthenticationModule } from './authentication/authentication.module';
import { UpdateAddressComponent } from './address/update-address/update-address.component';
import { AddressFormComponent } from './address/address-form/address-form.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { AddressTableComponent } from './address/address-table/address-table.component';
import { AddressListComponent } from './address/address-list/address-list.component';


@NgModule({
  declarations: [
    AppComponent,
    AddAddressComponent,
    UpdateAddressComponent,
    AddressFormComponent,
    AddressTableComponent,
    AddressListComponent
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
    AuthenticationModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule

  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
