import { NgModule, Optional, SkipSelf } from '@angular/core';
import { CommonModule } from '@angular/common';
// service
import { AuthenticationService } from './services/authentication.service';
import { AddressService } from './services/address.service';
// component
import { NavbarComponent } from './components/navbar/navbar.component';
import { MaterialModule } from '../material/material.module';



@NgModule({
  declarations: [NavbarComponent],
  imports: [
    CommonModule,
    MaterialModule,
  ],
  providers: [
    AuthenticationService,
    AddressService
  ],
  exports: [
    NavbarComponent
  ]

})
export class CoreModule {
  constructor( @Optional() @SkipSelf() parentModule: CoreModule) {
    if (parentModule) {
      throw new Error('CoreModule has already been loaded. You should only import Core modules in the AppModule only.');
    }
  }
}
