import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-income-by-locations',
  templateUrl: './income-by-locations.component.html',
  styleUrls: ['./income-by-locations.component.scss']
})
export class IncomeByLocationsComponent implements OnInit {
  title = 'Income by locations';
  type = 'BarChart';
  data = [
    ['Loc1', 500, ''],
    ['Loc2', 430, ''],
    ['Loc3', 600, ''],
    ['Loc4', 150, ''],
    ['Average', 700, 'color: red']
  ];
  columnNames = ['Location', 'Incomes', { role: 'style' }];
  options = {
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
