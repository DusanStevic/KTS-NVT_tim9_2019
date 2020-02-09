import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TicketsSoldByEventsComponent } from './tickets-sold-by-events.component';
import { Observable, of } from 'rxjs';
import { ChartService } from 'src/app/core/services/chart.service';


const chartServiceStub = {
  get() {
    const eventTickets: any[] = [
        {
          eventName: 'Event1',
          income: 10
        },
        {
          eventName: 'Event2',
          income: 20
        },
        {
          eventName: 'Average',
          income: 15
        },
      ];
    return of( eventTickets );
  }
};



describe('TicketsSoldByEventsComponent', () => {
  let component: TicketsSoldByEventsComponent;
  let fixture: ComponentFixture<TicketsSoldByEventsComponent>;
  let chartService: any;

  beforeEach((() => {
    const chartServiceMock = {
      getTicketsSoldByEvents: jasmine.createSpy('getTicketsSoldByEvents').and.returnValue(chartServiceStub.get())
    };

    TestBed.configureTestingModule({
      declarations: [ TicketsSoldByEventsComponent ],
      providers: [
        {provide: ChartService, useValue: chartServiceMock}
      ]
    });

    fixture = TestBed.createComponent(TicketsSoldByEventsComponent);
    component = fixture.componentInstance;
    chartService = TestBed.get(ChartService);

  }));


  it('should fetch the event incomes on init', async(() => {
    component.ngOnInit();

    expect(chartService.getTicketsSoldByEvents).toHaveBeenCalled();

    fixture.whenStable()
      .then(() => {
        expect(component.data.length).toEqual(3, 'should contain given amount of elements');
        expect(component.data[0][0]).toEqual('Event1'); // name
        expect(component.data[0][1]).toEqual(10); // income
        expect(component.data[0][2]).toEqual(''); // style
        expect(component.data[1][0]).toEqual('Event2'); // name
        expect(component.data[1][1]).toEqual(20); // income
        expect(component.data[1][2]).toEqual(''); // style
        expect(component.data[2][0]).toEqual('Average'); // name
        expect(component.data[2][1]).toEqual(15); // income
        expect(component.data[2][2]).toEqual('red'); // style
      });
  }));
});
