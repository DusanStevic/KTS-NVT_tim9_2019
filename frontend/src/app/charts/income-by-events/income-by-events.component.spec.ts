import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { IncomeByEventsComponent } from './income-by-events.component';
import { ChartIncomeEvents } from 'src/app/charts/model/chartIncomeEvents.model';
import { Observable, of } from 'rxjs';
import { ChartService } from 'src/app/core/services/chart.service';


const chartServiceStub = {
  get() {
    const eventIncomes: any[] = [
        {
          eventName: 'Event1',
          income: 1000.00
        },
        {
          eventName: 'Event2',
          income: 2000.00
        },
        {
          eventName: 'Average',
          income: 1500.00
        },
      ];
    return of( eventIncomes );
  }
};

describe('IncomeByEventsComponent', () => {
  let component: IncomeByEventsComponent;
  let fixture: ComponentFixture<IncomeByEventsComponent>;
  let chartService: any;

  beforeEach((() => {
    const chartServiceMock = {
      getIncomeByEvents: jasmine.createSpy('getIncomeByEvents').and.returnValue(chartServiceStub.get())
    };

    TestBed.configureTestingModule({
      declarations: [ IncomeByEventsComponent ],
      providers: [
        {provide: ChartService, useValue: chartServiceMock}
      ]
    });

    fixture = TestBed.createComponent(IncomeByEventsComponent);
    component = fixture.componentInstance;
    chartService = TestBed.get(ChartService);

  }));


  it('should fetch the event incomes on init', async(() => {
    component.ngOnInit();

    expect(chartService.getIncomeByEvents).toHaveBeenCalled();

    fixture.whenStable()
      .then(() => {
        expect(component.data.length).toEqual(3, 'should contain given amount of elements');
        expect(component.data[0][0]).toEqual('Event1'); // name
        expect(component.data[0][1]).toEqual(1000.00); // income
        expect(component.data[0][2]).toEqual(''); // style
        expect(component.data[1][0]).toEqual('Event2'); // name
        expect(component.data[1][1]).toEqual(2000.00); // income
        expect(component.data[1][2]).toEqual(''); // style
        expect(component.data[2][0]).toEqual('Average'); // name
        expect(component.data[2][1]).toEqual(1500.00); // income
        expect(component.data[2][2]).toEqual('red'); // style
      });
  }));
});
