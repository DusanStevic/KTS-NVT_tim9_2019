import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AddAddressComponent } from './address/add-address/add-address.component';
import { UpdateAddressComponent } from './address/update-address/update-address.component';
import { LoginComponent } from './authentication/login/login.component';
import { RegisterComponent } from './authentication/register/register.component';
import { AddressListComponent } from './address/address-list/address-list.component';
import { GoogleChartsComponent } from './charts/google-charts/google-charts.component';
import { EventListComponent } from './events/event-list/event-list.component';
import { RoleGuard } from 'src/app/core/guards/role.guard';
import { AddLocationComponent } from './location/add-location/add-location.component';
import { LocationListComponent } from './location/location-list/location-list.component';
import { UpdateLocationComponent } from './location/update-location/update-location.component';

const routes: Routes = [
  {path: '', redirectTo: '/events', pathMatch: 'full' },
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'events', component: EventListComponent},
  {
    path: 'address/add',
    component: AddAddressComponent,
    canActivate: [RoleGuard],
    data: {expectedRoles: 'ROLE_SYS_ADMIN|ROLE_ADMIN'}
  },
  {
    path: 'address/update',
    component: UpdateAddressComponent,
    canActivate: [RoleGuard],
    data: {expectedRoles: 'ROLE_SYS_ADMIN|ROLE_ADMIN'}
  },
  {
    path: 'address/all',
    component: AddressListComponent
  },
  {
    path: 'charts',
    component: GoogleChartsComponent,
    canActivate: [RoleGuard],
    data: {expectedRoles: 'ROLE_SYS_ADMIN|ROLE_ADMIN'}
  },
  {
    path: 'location/add',
    component: AddLocationComponent,
    canActivate: [RoleGuard],
    data: {expectedRoles: 'ROLE_SYS_ADMIN|ROLE_ADMIN'}
  },
  {
    path: 'location/update',
    component: UpdateLocationComponent,
    canActivate: [RoleGuard],
    data: {expectedRoles: 'ROLE_SYS_ADMIN|ROLE_ADMIN'}
  },
  {
    path: 'location/all',
    component: LocationListComponent
  }
];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
