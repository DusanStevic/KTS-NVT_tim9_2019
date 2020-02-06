import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReservationDetailedComponent } from './reservation-detailed.component';

describe('ReservationDetailedComponent', () => {
  let component: ReservationDetailedComponent;
  let fixture: ComponentFixture<ReservationDetailedComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReservationDetailedComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReservationDetailedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
