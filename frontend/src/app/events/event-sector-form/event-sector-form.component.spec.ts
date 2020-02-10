import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EventSectorFormComponent } from './event-sector-form.component';

describe('EventSectorFormComponent', () => {
  let component: EventSectorFormComponent;
  let fixture: ComponentFixture<EventSectorFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EventSectorFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EventSectorFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
