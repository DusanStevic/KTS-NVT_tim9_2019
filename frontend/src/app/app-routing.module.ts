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
import { LocationDetailsComponent } from './location/location-details/location-details.component';
import { ProfileComponent } from './user/profile/profile.component';
import { ChangePasswordComponent } from './user/change-password/change-password.component';
import { UpdateHallComponent } from './location/update-hall/update-hall.component';
import { UpdateSectorComponent } from './location/update-sector/update-sector.component';
import { MyReservationsComponent } from './reservation/my-reservations/my-reservations.component';
import { AddAdminComponent } from './user/add-admin/add-admin.component';
import { ViewReservationComponent } from './reservation/view-reservation/view-reservation.component';
import { EventDetailsComponent } from './events/event-details/event-details.component';
import { MakeReservationComponent } from './reservation/make-reservation/make-reservation.component';
import { AddEventComponent } from './events/add-event/add-event.component';
import { UpdateEventComponent } from './events/update-event/update-event.component';


const routes: Routes = [
  // {path: '', redirectTo: '/events', pathMatch: 'full' },
  {path: '', component: EventListComponent },
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'events', component: EventListComponent},
  {path: 'eventDetail/:id', component: EventDetailsComponent},
  {
    path: 'profile',
    component: ProfileComponent,
    canActivate: [RoleGuard],
    data: {expectedRoles: 'ROLE_SYS_ADMIN|ROLE_ADMIN|ROLE_REGISTERED_USER'}
  },
  {
    path: 'myReservations',
    component: MyReservationsComponent,
    canActivate: [RoleGuard],
    data: {expectedRoles: 'ROLE_REGISTERED_USER'}
  },
  {
    path: 'reservationDetail/:id',
    component: ViewReservationComponent,
    canActivate: [RoleGuard],
    data: {expectedRoles: 'ROLE_REGISTERED_USER'}
  },
  {
    path: 'changePassword',
    component: ChangePasswordComponent,
    canActivate: [RoleGuard],
    data: {expectedRoles: 'ROLE_SYS_ADMIN|ROLE_ADMIN|ROLE_REGISTERED_USER'}
  },
  {
    path: 'address/add',
    component: AddAddressComponent,
    canActivate: [RoleGuard],
    data: {expectedRoles: 'ROLE_SYS_ADMIN|ROLE_ADMIN'}
  },
  {
    path: 'admin/add',
    component: AddAdminComponent,
    canActivate: [RoleGuard],
    data: {expectedRoles: 'ROLE_SYS_ADMIN'}
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
    component: LocationListComponent,
    canActivate: [RoleGuard],
    data: {expectedRoles: 'ROLE_SYS_ADMIN|ROLE_ADMIN'}
  },
  {
    path: 'location/details',
    component: LocationDetailsComponent
  },
  {
    path: 'hall/update',
    component: UpdateHallComponent,
    canActivate: [RoleGuard],
    data: {expectedRoles: 'ROLE_SYS_ADMIN|ROLE_ADMIN'}
  },
  {
    path: 'sector/update',
    component: UpdateSectorComponent,
    canActivate: [RoleGuard],
    data: {expectedRoles: 'ROLE_SYS_ADMIN|ROLE_ADMIN'}
  },
  {
    path: 'reserve',
    component: MakeReservationComponent,
    canActivate: [RoleGuard],
    data: {expectedRoles: 'ROLE_REGISTERED_USER'}
  },
  {
    path: 'events/add',
    component: AddEventComponent,
    canActivate: [RoleGuard],
    data: {expectedRoles: 'ROLE_SYS_ADMIN|ROLE_ADMIN'}
  },
  {
    path: 'events/update',
    component: UpdateEventComponent,
    canActivate: [RoleGuard],
    data: {expectedRoles: 'ROLE_SYS_ADMIN|ROLE_ADMIN'}
  }
];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
