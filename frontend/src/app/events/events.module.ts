import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EventListComponent } from './event-list/event-list.component';
import { MaterialModule } from '../material/material.module';
import { EventSearchComponent } from './event-search/event-search.component';
import { AppRoutingModule } from '../app-routing.module';
import { EventDetailsComponent } from './event-details/event-details.component';



@NgModule({
  declarations: [EventListComponent, EventSearchComponent, EventDetailsComponent],
  imports: [
    CommonModule,
    MaterialModule,
    AppRoutingModule
  ],
  exports: [EventListComponent, EventSearchComponent, EventDetailsComponent]
})
export class EventsModule { }
