import { Component, OnInit } from '@angular/core';

import {SystemInformations } from '../model/systemInformations.model'
import { ChartIncomeEvents} from '../model/chartIncomeEvents.model'
import { ChartIncomeLocations} from '../model/chartIncomeLocations.model'
import { ChartTicketsSoldEvents} from '../model/chartTicketsSoldEvents.model'
import { ChartTicketsSoldLocations} from '../model/chartTicketsSoldLocations.model'

import {ChartService} from "../chart.service"

@Component({
  selector: 'app-google-charts',
  templateUrl: './google-charts.component.html',
  styleUrls: ['./google-charts.component.scss']
})
export class GoogleChartsComponent implements OnInit {

  chart1 : SystemInformations;
  chart2 : Array<Array<ChartIncomeEvents>>;
  chart3 : Array<Array<ChartTicketsSoldEvents>>[];
  chart4 : Array<Array<ChartIncomeLocations[]>>;
  chart5 : Array<Array<ChartTicketsSoldLocations[]>>;


  constructor(
    private chartService : ChartService
  ) {}

  ngOnInit() {
  }

  renderSysInfo(){
    this.chartService.getSysInfo().subscribe(
      result => {
        console.log("Succesful sysinfo");
        console.log(result);
        this.chart1 = result.body as SystemInformations;
      }
    )
  }

  renderIncomesEvents(){
    this.chartService.getIncomeByEvents().subscribe(
      result => {
        let arr = [];
        result.forEach(element => {
          if(element.eventName === "Average"){
					  arr.push([element.eventName, element.income , 'red']);
          }else{
					  arr.push([element.eventName, element.income , '']);
          }
        });
        console.log("Succesful income events");
        console.log(arr);
        this.chart2 = arr;
      }
    )
  }

  renderTicketsSoldEvents()
  {
    this.chartService.getTicketsSoldByEvents().subscribe(
      result => {
        let arr = [];
        result.forEach(element => {
          if(element.eventName === "Average"){
					  arr.push([element.eventName, element.ticketsSold , 'red']);
          }else{
					  arr.push([element.eventName, element.ticketsSold , '']);
          }
        });
        console.log("Succesful tickets sold events");
        console.log(arr);
        this.chart3 = arr;
      }
    )
  }

  renderIncomesLocations(){
    this.chartService.getIncomeByLocations().subscribe(
      result => {
        let arr = [];
        result.forEach(element => {
          if(element.locationName === "Average"){
					  arr.push([element.locationName, element.income , 'red']);
          }else{
					  arr.push([element.locationName, element.income , '']);
          }
        });
        console.log("Succesful income locations");
        console.log(arr);
        this.chart4 = arr;
      }
    )
  }

  renderTicketsSoldLocations()
  {
    this.chartService.getTicketsSoldByLocations().subscribe(
      result => {
        let arr = [];
        result.forEach(element => {
          if(element.locationName === "Average"){
					  arr.push([element.locationName, element.ticketsSold , 'red']);
          }else{
					  arr.push([element.locationName, element.ticketsSold , '']);
          }
        });
        console.log("Succesful tickets sold locations");
        console.log(result);
        this.chart5 = arr;
      }
    )
  }


  renderCharts(){
    this.renderSysInfo();
    this.renderIncomesEvents();
    this.renderTicketsSoldEvents();
    this.renderIncomesLocations();
    this.renderTicketsSoldLocations();
  }

  
  title = 'Income by events';  
  type = 'BarChart';  
  data = [  
    ["Event1", 500],  
    ["Event2", 430],  
    ["Event3", 600],  
    ["Event4", 150],  
    ["Event5", 700]  
  ];  
  columnNames = ['Event', 'Incomes'];  
  options = {
    hAxis: {
      title: 'Income',
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

}
