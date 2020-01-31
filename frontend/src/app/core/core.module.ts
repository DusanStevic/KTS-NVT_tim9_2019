import { NgModule, Optional, SkipSelf } from '@angular/core';
import { CommonModule } from '@angular/common';
// service
import { AuthenticationService } from './services/authentication.service';



@NgModule({
  declarations: [],
  imports: [
    CommonModule
  ],
  providers: [
    AuthenticationService
  ]

})
export class CoreModule {
  constructor( @Optional() @SkipSelf() parentModule: CoreModule) {
    if (parentModule) {
      throw new Error('CoreModule has already been loaded. You should only import Core modules in the AppModule only.');
    }
  }
}
