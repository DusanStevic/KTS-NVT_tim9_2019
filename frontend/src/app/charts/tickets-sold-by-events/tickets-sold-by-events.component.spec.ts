import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TicketsSoldByEventsComponent } from './tickets-sold-by-events.component';

describe('TicketsSoldByEventsComponent', () => {
  let component: TicketsSoldByEventsComponent;
  let fixture: ComponentFixture<TicketsSoldByEventsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TicketsSoldByEventsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TicketsSoldByEventsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
