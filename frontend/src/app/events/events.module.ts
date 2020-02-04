import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EventListComponent } from './event-list/event-list.component';
import { MaterialModule } from '../material/material.module';



@NgModule({
  declarations: [EventListComponent],
  imports: [
    CommonModule,
    MaterialModule
  ],
  exports: [EventListComponent]
})
export class EventsModule { }
