import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-tickets-sold-by-locations',
  templateUrl: './tickets-sold-by-locations.component.html',
  styleUrls: ['./tickets-sold-by-locations.component.scss']
})
export class TicketsSoldByLocationsComponent implements OnInit {
  title = 'Number of tickets sold by locations';  
  type = 'BarChart';  
  data = [  
    ["Loc1", 10, ''],  
    ["Loc2", 8, ''],  
    ["Loc3", 90, ''],  
    ["Loc4", 2, ''],  
    ["Average", 42.3, 'color: red']  
  ];  
  columnNames = ['Location', 'Tickets sold',{ role: 'style' }];  
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
      title: 'Locations' ,
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
