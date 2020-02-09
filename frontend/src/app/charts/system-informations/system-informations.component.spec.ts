import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SystemInformationsComponent } from './system-informations.component';

describe('SystemInformationsComponent', () => {
  let component: SystemInformationsComponent;
  let fixture: ComponentFixture<SystemInformationsComponent>;
  let chartService: any;
  

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SystemInformationsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SystemInformationsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
