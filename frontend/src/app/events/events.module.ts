import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EventListComponent } from './event-list/event-list.component';
import { MaterialModule } from '../material/material.module';
import { EventSearchComponent } from './event-search/event-search.component';
import { EventDetailsComponent } from './events/event-details/event-details.component';



@NgModule({
  declarations: [EventListComponent, EventSearchComponent, EventDetailsComponent],
  imports: [
    CommonModule,
    MaterialModule
  ],
  exports: [EventListComponent, EventSearchComponent]
})
export class EventsModule { }
