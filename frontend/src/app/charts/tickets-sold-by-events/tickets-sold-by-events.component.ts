import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-tickets-sold-by-events',
  templateUrl: './tickets-sold-by-events.component.html',
  styleUrls: ['./tickets-sold-by-events.component.scss']
})
export class TicketsSoldByEventsComponent implements OnInit {
  title = 'Number of tickets sold by events';  
  type = 'BarChart';  
  data = [  
    ["Loc1", 10, ''],  
    ["Loc2", 8, ''],  
    ["Loc3", 90, ''],  
    ["Loc4", 2, ''],  
    ["Average", 42.3, 'color: red']  
  ];  
  columnNames = ['Event', 'Tickets sold',{ role: 'style' }];  
  options = {
    hAxis: {
      title: 'Tickets sold',
      minValue:0,
        titleTextStyle: {
          fontSize: 15,
          bold : true
        }
    },
    vAxis: {
      title: 'Events' ,
      textPosition : 'out',
      textStyle : {
        fontSize : 15,
        bold : true
      },
      titleTextStyle: {
        fontSize: 15
      }
    },
  };  
  width = 600;  
  height = 400;  

  constructor() { }

  ngOnInit() {
  }

}
