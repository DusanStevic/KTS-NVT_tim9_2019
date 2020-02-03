import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UpdateAddressComponent } from './update-address/update-address.component';
import { AddressFormComponent } from './address-form/address-form.component';
import { AddressTableComponent } from './address-table/address-table.component';
import { AddressListComponent } from './address-list/address-list.component';
import { AddAddressComponent } from './add-address/add-address.component';
import { MaterialModule } from '../material/material.module';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    AddAddressComponent,
    UpdateAddressComponent,
    AddressTableComponent,
    AddressFormComponent,
    AddressListComponent
  ],
  imports: [
    CommonModule,
    MaterialModule,
    ReactiveFormsModule,
    FormsModule
  ],
  exports: [
    UpdateAddressComponent,
    AddressFormComponent,
    AddressTableComponent,
    AddressListComponent,
    AddAddressComponent
  ]
})
export class AddressModule { }
