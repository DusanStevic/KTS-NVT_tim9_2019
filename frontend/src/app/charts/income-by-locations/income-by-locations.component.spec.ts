import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { IncomeByLocationsComponent } from './income-by-locations.component';
import { of } from 'rxjs';
import { ChartService } from 'src/app/core/services/chart.service';

const chartServiceStub = {
  get() {
    const locationIncomes: any[] = [
        {
          eventName: 'Location1',
          income: 1000.00
        },
        {
          eventName: 'Location2',
          income: 2000.00
        },
        {
          eventName: 'Average',
          income: 1500.00
        },
      ];
    return of( locationIncomes );
  }
};

describe('IncomeByLocationsComponent', () => {
  let component: IncomeByLocationsComponent;
  let fixture: ComponentFixture<IncomeByLocationsComponent>; let chartService: any;

  beforeEach((() => {
    const chartServiceMock = {
      getIncomeByLocations: jasmine.createSpy('getIncomeByLocations').and.returnValue(chartServiceStub.get())
    };

    TestBed.configureTestingModule({
      declarations: [ IncomeByLocationsComponent ],
      providers: [
        {provide: ChartService, useValue: chartServiceMock}
      ]
    });

    fixture = TestBed.createComponent(IncomeByLocationsComponent);
    component = fixture.componentInstance;
    chartService = TestBed.get(ChartService);

  }));


  it('should fetch the location incomes on init', async(() => {
    component.ngOnInit();

    expect(chartService.getIncomeByLocations).toHaveBeenCalled();

    fixture.whenStable()
      .then(() => {
        expect(component.data.length).toEqual(3, 'should contain given amount of elements');
        expect(component.data[0][0]).toEqual('Location1'); // name
        expect(component.data[0][1]).toEqual(1000.00); // income
        expect(component.data[0][2]).toEqual(''); // style
        expect(component.data[1][0]).toEqual('Location2'); // name
        expect(component.data[1][1]).toEqual(2000.00); // income
        expect(component.data[1][2]).toEqual(''); // style
        expect(component.data[2][0]).toEqual('Average'); // name
        expect(component.data[2][1]).toEqual(1500.00); // income
        expect(component.data[2][2]).toEqual('red'); // style
      });
  }));

});
