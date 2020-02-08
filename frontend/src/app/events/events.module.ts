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
import { UpdateEventComponent } from './update-event/update-event.component';



@NgModule({
  declarations: [
    EventListComponent,
    EventSearchComponent,
    EventDetailsComponent,
    EventFormComponent,
    AddEventComponent,
    UpdateEventComponent
  ],
  imports: [
    CommonModule,
    MaterialModule,
    AppRoutingModule,
    ReactiveFormsModule,
    FormsModule
  ],
  exports: [EventListComponent, EventSearchComponent, EventDetailsComponent]
})
export class EventsModule { }
