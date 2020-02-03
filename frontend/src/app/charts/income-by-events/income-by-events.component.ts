import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-income-by-events',
  templateUrl: './income-by-events.component.html',
  styleUrls: ['./income-by-events.component.scss']
})
export class IncomeByEventsComponent implements OnInit {
  title = 'Income by events';
  type = 'BarChart';
  data = [
    ['Event1', 500, ''],
    ['Event2', 430, ''],
    ['Event3', 600, ''],
    ['Event4', 150, ''],
    ['Event5', 700, 'color: red']
  ];
  columnNames = ['Event', 'Incomes', { role: 'style' }];
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
