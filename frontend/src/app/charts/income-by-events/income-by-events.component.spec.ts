import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { IncomeByEventsComponent } from './income-by-events.component';

describe('IncomeByEventsComponent', () => {
  let component: IncomeByEventsComponent;
  let fixture: ComponentFixture<IncomeByEventsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ IncomeByEventsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(IncomeByEventsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
