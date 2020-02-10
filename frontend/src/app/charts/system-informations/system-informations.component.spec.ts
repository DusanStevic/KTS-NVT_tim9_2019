import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SystemInformationsComponent } from './system-informations.component';
import { SystemInformations } from 'src/app/charts/model/systemInformations.model';

import { Observable, of } from 'rxjs';
import { ChartService } from 'src/app/core/services/chart.service';


const chartServiceStub = {
  get() {
    const sysinfo: SystemInformations = {
      numberOfEvents: 5,
      numberOfAdmins: 5,
      numberOfUsers: 5,
      allTimeIncome: 1566.00,
      allTimeTickets: 20
    };
    return of( sysinfo );
  }
};

describe('SystemInformationsComponent', () => {
  let component: SystemInformationsComponent;
  let fixture: ComponentFixture<SystemInformationsComponent>;
  let chartService: any;

  beforeEach((() => {
    const chartServiceMock = {
      getSysInfo: jasmine.createSpy('getSysInfo').and.returnValue(chartServiceStub.get())
    };

    TestBed.configureTestingModule({
      declarations: [ SystemInformationsComponent ],
      providers: [
        {provide: ChartService, useValue: chartServiceMock}
      ]
    });

    fixture = TestBed.createComponent(SystemInformationsComponent);
    component = fixture.componentInstance;
    chartService = TestBed.get(ChartService);

  }));

  it('should fetch the system informations on init', async(() => {
    component.ngOnInit();

    expect(chartService.getSysInfo).toHaveBeenCalled();

    fixture.whenStable()
      .then(() => {
        expect(component.sysInfo.numberOfAdmins).toBe(5);
        expect(component.sysInfo.numberOfEvents).toBe(5);
        expect(component.sysInfo.numberOfUsers).toBe(5);
        expect(component.sysInfo.allTimeIncome).toBe(1566.00);
        expect(component.sysInfo.allTimeTickets).toBe(20);

      });
  }));
});
