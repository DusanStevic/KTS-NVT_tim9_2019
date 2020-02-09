import { Component, OnInit } from '@angular/core';
import {ChartService} from '../../core/services/chart.service';
import { ChartIncomeEvents} from '../model/chartIncomeEvents.model';

@Component({
  selector: 'app-income-by-events',
  templateUrl: './income-by-events.component.html',
  styleUrls: ['./income-by-events.component.scss']
})
export class IncomeByEventsComponent implements OnInit {
  title = 'Income by events';
  type = 'BarChart';
  data: Array<Array<ChartIncomeEvents>>;
  columnNames = ['Event', 'Incomes', { role: 'style' }];
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
      title: 'Events' ,
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
    this.renderIncomesEvents();
  }

  renderIncomesEvents() {
    this.chartService.getIncomeByEvents().subscribe(
      result => {
        const arr = [];
        result.forEach((element: { eventName: string; income: any; }) => {
            if (element.eventName === 'Average') {
              arr.push([element.eventName, element.income , 'red']);
            } else {
              arr.push([element.eventName, element.income , '']);
            }
        });
        console.log('Succesful income events');
        console.log(arr);
        this.data = arr;
      }
    );
  }

}
