import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EventListComponent } from './event-list/event-list.component';
import { MaterialModule } from '../material/material.module';
import { EventSearchComponent } from './event-search/event-search.component';
import { AppRoutingModule } from '../app-routing.module';
import { EventDetailsComponent } from './event-details/event-details.component';
import { EventFormComponent } from './event-form/event-form.component';
import { AddEventComponent } from './add-event/add-event.component';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';



@NgModule({
  declarations: [EventListComponent, EventSearchComponent, EventDetailsComponent, EventFormComponent, AddEventComponent],
  imports: [
    CommonModule,
    MaterialModule,
    AppRoutingModule,
    ReactiveFormsModule,
    FormsModule
  ],
  exports: [EventListComponent, EventSearchComponent, EventDetailsComponent, EventFormComponent, AddEventComponent]
})
export class EventsModule { }
