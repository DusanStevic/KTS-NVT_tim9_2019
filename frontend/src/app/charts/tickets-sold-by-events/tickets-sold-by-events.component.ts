import { Component, OnInit, Input } from '@angular/core';

import {SystemInformations } from '../model/systemInformations.model';
import { ChartIncomeEvents} from '../model/chartIncomeEvents.model';
import { ChartIncomeLocations} from '../model/chartIncomeLocations.model';
import { ChartTicketsSoldEvents} from '../model/chartTicketsSoldEvents.model';
import { ChartTicketsSoldLocations} from '../model/chartTicketsSoldLocations.model';

import {ChartService} from '../../core/services/chart.service';
@Component({
  selector: 'app-tickets-sold-by-events',
  templateUrl: './tickets-sold-by-events.component.html',
  styleUrls: ['./tickets-sold-by-events.component.scss']
})
export class TicketsSoldByEventsComponent implements OnInit {
  title = 'Number of tickets sold by events';
  type = 'BarChart';
  data: Array<Array<ChartTicketsSoldEvents>>; /* [
    ['Loc1', 10, ''],
    ['Loc2', 8, ''],
    ['Loc3', 90, ''],
    ['Loc4', 2, ''],
    ['Average', 42.3, 'color: red']
  ];*/
  columnNames = ['Event', 'Tickets sold', { role: 'style' }];
  options = {
    titleTextStyle: {
      fontSize: 25,
      bold : true
    },
    hAxis: {
      title: 'Tickets sold',
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
    this.renderTicketsSoldEvents();
  }

  renderTicketsSoldEvents() {
    this.chartService.getTicketsSoldByEvents().subscribe(
      result => {
        const arr = [];
        result.forEach((element: { eventName: string; ticketsSold: any; }) => {
          if (element.eventName === 'Average') {
            arr.push([element.eventName, element.ticketsSold , 'red']);
          } else {
            arr.push([element.eventName, element.ticketsSold , '']);
          }
        });
        console.log('Succesful tickets sold events');
        console.log(arr);
        this.data = arr;
      }
    );
  }
}
