import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AddAddressComponent } from './address/add-address/add-address.component';
import { UpdateAddressComponent } from './address/update-address/update-address.component';
import { AppComponent } from './app.component';


const routes: Routes = [
  {
    path: 'address/add',
    component: AddAddressComponent,
    data: {expectedRoles: 'ROLE_SYS_ADMIN|ROLE_ADMIN'}
  },
  {
    path: 'address/update',
    component: UpdateAddressComponent,
    data: {expectedRoles: 'ROLE_SYS_ADMIN|ROLE_ADMIN'}
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
