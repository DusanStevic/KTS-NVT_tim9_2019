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

  chart1: SystemInformations ;

  constructor(
    private chartService: ChartService
  ) {
    this.chart1 = {
      allTimeIncome: 0,
      allTimeTickets: 0,
      numberOfAdmins: 0,
      numberOfEvents: 0,
      numberOfUsers: 0
    };
  }

  ngOnInit() {
    this.renderSysInfo();
  }

  renderSysInfo() {
    this.chartService.getSysInfo().subscribe(
      result => {
        console.log('Succesful sysinfo');
        console.log(result);
        this.chart1 = result as SystemInformations;
      }
    );
  }
}
