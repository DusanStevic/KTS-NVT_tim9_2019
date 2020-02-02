import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AddAddressComponent } from './address/add-address/add-address.component';
import { UpdateAddressComponent } from './address/update-address/update-address.component';
import { LoginComponent } from './authentication/login/login.component';
import { RegisterComponent } from './authentication/register/register.component';


const routes: Routes = [
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
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
