import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EventListComponent } from './event-list/event-list.component';
import { LocationModule } from '../location/location.module';



@NgModule({
  declarations: [EventListComponent],
  imports: [
    CommonModule,
    LocationModule
  ],
  exports: [EventListComponent]
})
export class EventsModule { }
