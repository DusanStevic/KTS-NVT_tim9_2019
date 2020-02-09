import { Component, OnInit } from '@angular/core';

import {SystemInformations } from '../model/systemInformations.model';
import { ChartIncomeEvents} from '../model/chartIncomeEvents.model';
import { ChartIncomeLocations} from '../model/chartIncomeLocations.model';
import { ChartTicketsSoldEvents} from '../model/chartTicketsSoldEvents.model';
import { ChartTicketsSoldLocations} from '../model/chartTicketsSoldLocations.model';

import {ChartService} from '../../core/services/chart.service';
@Component({
  selector: 'app-income-by-locations',
  templateUrl: './income-by-locations.component.html',
  styleUrls: ['./income-by-locations.component.scss']
})
export class IncomeByLocationsComponent implements OnInit {
  title = 'Income by locations';
  type = 'BarChart';
  data: Array<Array<any>>;
  columnNames = ['Location', 'Incomes', { role: 'style' }];
  options = {
    titleTextStyle: {
      fontSize: 25,
      bold : true
    },
    hAxis: {
      title: 'Income',
      minValue: 0,
        titleTextStyle: {
          fontSize: 15,
          bold : true
        }
    },
    vAxis: {
      title: 'Locations' ,
      textPosition : 'out',
      titleTextStyle: {
        fontSize: 15,
        bold : true
      }
    },
  };
  width = 600;
  height = 400;

  constructor(
    private chartService: ChartService
  ) { }

  ngOnInit() {
    this.renderIncomesLocations();
  }

  renderIncomesLocations() {
    this.chartService.getIncomeByLocations().subscribe(
      result => {
        const arr = [];
        result.forEach((element: { locationName: string; income: any; }) => {
          if (element.locationName === 'Average') {
            arr.push([element.locationName, element.income , 'red']);
          } else {
            arr.push([element.locationName, element.income , '']);
          }
        });
        console.log('Succesful income locations');
        console.log(arr);
        this.data = arr;
      }
    );
  }

}
