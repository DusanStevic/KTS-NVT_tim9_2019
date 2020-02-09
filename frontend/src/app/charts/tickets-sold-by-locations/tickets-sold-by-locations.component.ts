import { Component, OnInit, Input } from '@angular/core';
import { ChartTicketsSoldLocations} from '../model/chartTicketsSoldLocations.model';

import {ChartService} from '../../core/services/chart.service';
@Component({
  selector: 'app-tickets-sold-by-locations',
  templateUrl: './tickets-sold-by-locations.component.html',
  styleUrls: ['./tickets-sold-by-locations.component.scss']
})
export class TicketsSoldByLocationsComponent implements OnInit {
  title = 'Number of tickets sold by locations';
  type = 'BarChart';
  data: Array<Array<ChartTicketsSoldLocations[]>>;
  columnNames = ['Location', 'Tickets sold', { role: 'style' }];
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
    this.renderTicketsSoldLocations();
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
        this.data = arr;
      }
    );
  }

}
