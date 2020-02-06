import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { IncomeByLocationsComponent } from './income-by-locations.component';

describe('IncomeByLocationsComponent', () => {
  let component: IncomeByLocationsComponent;
  let fixture: ComponentFixture<IncomeByLocationsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ IncomeByLocationsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(IncomeByLocationsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
