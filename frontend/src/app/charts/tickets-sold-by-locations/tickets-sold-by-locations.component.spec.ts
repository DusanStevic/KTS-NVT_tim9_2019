import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TicketsSoldByLocationsComponent } from './tickets-sold-by-locations.component';

describe('TicketsSoldByLocationsComponent', () => {
  let component: TicketsSoldByLocationsComponent;
  let fixture: ComponentFixture<TicketsSoldByLocationsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TicketsSoldByLocationsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TicketsSoldByLocationsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
