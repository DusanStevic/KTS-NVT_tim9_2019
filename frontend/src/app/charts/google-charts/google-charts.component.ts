import { Component, OnInit } from '@angular/core';

import {SystemInformations } from '../model/systemInformations.model';
import { ChartIncomeEvents} from '../model/chartIncomeEvents.model';
import { ChartIncomeLocations} from '../model/chartIncomeLocations.model';
import { ChartTicketsSoldEvents} from '../model/chartTicketsSoldEvents.model';
import { ChartTicketsSoldLocations} from '../model/chartTicketsSoldLocations.model';

import {ChartService} from '../../core/services/chart.service';

@Component({
  selector: 'app-google-charts',
  templateUrl: './google-charts.component.html',
  styleUrls: ['./google-charts.component.scss']
})
export class GoogleChartsComponent implements OnInit {


  constructor(
    private chartService: ChartService
  ) {}

  chart1: SystemInformations ;
  chart2: Array<Array<ChartIncomeEvents>>;
  chart3: Array<Array<ChartTicketsSoldEvents>>[];
  chart4: Array<Array<ChartIncomeLocations[]>>;
  chart5: Array<Array<ChartTicketsSoldLocations[]>>;


  ngOnInit() {
  }

  renderSysInfo() {
    this.chartService.getSysInfo().subscribe(
      result => {
        console.log('Succesful sysinfo');
        console.log(result);
        this.chart1 = result.body as SystemInformations;
      }
    );
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
        this.chart2 = arr;
      }
    );
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
        this.chart3 = arr;
      }
    );
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
        this.chart4 = arr;
      }
    );
  }

  renderTicketsSoldLocations() {
    this.chartService.getTicketsSoldByLocations().subscribe(
      result => {
        const arr = [];
        result.forEach((element: { locationName: string; ticketsSold: any; }) => {
          if (element.locationName === 'Average') {
            arr.push([element.locationName, element.ticketsSold , 'red']);
          } else {
            arr.push([element.locationName, element.ticketsSold , '']);
          }
        });
        console.log('Succesful tickets sold locations');
        console.log(result);
        this.chart5 = arr;
      }
    );
  }


  renderCharts() {
    this.renderSysInfo();
    this.renderIncomesEvents();
    this.renderTicketsSoldEvents();
    this.renderIncomesLocations();
    this.renderTicketsSoldLocations();
  }

}
