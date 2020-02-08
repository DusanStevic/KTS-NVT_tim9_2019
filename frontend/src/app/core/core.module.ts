import { NgModule, Optional, SkipSelf } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AppRoutingModule } from 'src/app/app-routing.module';
import { AuthenticationService } from './services/authentication.service';
import { AddressService } from './services/address.service';
import { MaterialModule } from '../material/material.module';
import { NavbarRegisteredUserComponent } from './components/navbar-registered-user/navbar-registered-user.component';
import { NavbarUserComponent } from './components/navbar-user/navbar-user.component';
import { NavbarAdminComponent } from './components/navbar-admin/navbar-admin.component';
import { NavbarSysAdminComponent } from './components/navbar-sys-admin/navbar-sys-admin.component';
import { ChartService } from './services/chart.service';
import { ConstantsService } from './services/constants.service';
import { LocationService } from './services/location.service';
import { UserService } from './services/user.service';
import { MapService } from './services/map.service';
import { FileUploadService } from './services/file-upload.service';
import { PaypalService } from './services/paypal.service';
import { EventService } from './services/event.service';



@NgModule({
  declarations: [NavbarRegisteredUserComponent, NavbarUserComponent, NavbarAdminComponent, NavbarSysAdminComponent],
  imports: [
    CommonModule,
    MaterialModule,
    AppRoutingModule
  ],
  providers: [
    AuthenticationService,
    AddressService,
    ChartService,
    ConstantsService,
    LocationService,
    UserService,
    MapService,
    FileUploadService,
    PaypalService,
    EventService
  ],
  exports: [
     NavbarRegisteredUserComponent, NavbarUserComponent, NavbarAdminComponent, NavbarSysAdminComponent
  ]

})
export class CoreModule {
  constructor( @Optional() @SkipSelf() parentModule: CoreModule) {
    if (parentModule) {
      throw new Error('CoreModule has already been loaded. You should only import Core modules in the AppModule only.');
    }
  }
}
