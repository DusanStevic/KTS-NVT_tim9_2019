import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HallTableComponent } from './hall-table.component';

describe('HallTableComponent', () => {
  let component: HallTableComponent;
  let fixture: ComponentFixture<HallTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HallTableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HallTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
