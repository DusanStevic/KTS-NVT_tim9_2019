import { TestBed } from '@angular/core/testing';

import { HttpClientModule, HttpParams, HttpClient, HttpHeaders, HttpResponse, HttpBackend, HttpRequest,  } from '@angular/common/http';
import { HttpTestingController } from '@angular/common/http/testing';
import {async, fakeAsync, tick} from '@angular/core/testing';
import { ChartService } from './chart.service';
import { ConstantsService } from './constants.service';
import { SystemInformations } from 'src/app/charts/model/systemInformations.model';
import { ChartIncomeEvents } from 'src/app/charts/model/chartIncomeEvents.model';
import { ChartIncomeLocations } from 'src/app/charts/model/chartIncomeLocations.model';
import { ChartTicketsSoldEvents } from 'src/app/charts/model/chartTicketsSoldEvents.model';
import { ChartTicketsSoldLocations } from 'src/app/charts/model/chartTicketsSoldLocations.model';

import { Observable } from 'rxjs';

describe('ChartService', () => {

  beforeEach(() => {
    TestBed.configureTestingModule({
    providers: [
      {provide: HttpBackend, useClass: HttpTestingController },
      {provide: HttpRequest, useClass:  HttpRequest},
      HttpClient,
      ChartService,
      ConstantsService
    ]
  });
    this.chartService = TestBed.get(ChartService);
    this.constantService = TestBed.get(ConstantsService);
    this.backend = TestBed.get(HttpBackend);
    this.backend.connections.subscribe((connection: any) =>
       this.lastConnection = connection);
  });

  it('should pass a test', () => {
    expect(true).toBe(true);
  });

  it('getSysInfo() should query current service url', () => {
    this.chartService.getSysInfo();
    expect(this.lastConnection).toBeDefined('no http service connection at all');
    expect(this.lastConnection.url).toMatch( this.constantsService.chartsPath + '/sysinfo', 'url invalid');
  });

  it('getSysInfo() should return Observable of SystemInformations', () => {
    let sysinfo: SystemInformations;
    this.chartService.getSysInfo().then((data: SystemInformations) => sysinfo = data);
    this.lastConnection.mockRespond(new HttpResponse({
      body: JSON.stringify({
        numberOfEvents: 5,
        numberOfAdmins: 5,
        numberOfUsers: 5,
        allTimeIncome: 1566.00,
        allTimeTickets: 20
      })
    }));
    tick();
    expect(sysinfo.numberOfEvents).toEqual(5);
    expect(sysinfo.numberOfEvents).toEqual(5);
    expect(sysinfo.numberOfEvents).toEqual(5);
    expect(sysinfo.numberOfEvents).toEqual(1566.00);
    expect(sysinfo.numberOfEvents).toEqual(20);
  });

  it('getIncomeByEvents() should query current service url', () => {
    this.chartService.getIncomeByEvents();
    expect(this.lastConnection).toBeDefined('no http service connection at all');
    expect(this.lastConnection.url).toMatch( this.constantsService.chartsPath + '/event_incomes', 'url invalid');
  });

  it('getIncomeByEvents() should return a ChartIncomeEvents[]', () => {
    let eventIncomes: ChartIncomeEvents[];
    this.chartService.getIncomeByEvents().then((data: ChartIncomeEvents[]) => eventIncomes = data);
    this.lastConnection.mockRespond(new HttpResponse({
      body: JSON.stringify([
        {
          eventName: 'Event1',
          income: 1000.00
        },
        {
          eventName: 'Event2',
          income: 2000.00
        },
        {
          eventName: 'Average',
          income: 1500.00
        },
      ])
    }));
    tick();
    expect(eventIncomes.length).toEqual(3, 'should contain given amount of elements');
    expect(eventIncomes[0].eventName).toEqual('Event1');
    expect(eventIncomes[0].income).toEqual(1000);
    expect(eventIncomes[1].eventName).toEqual('Event2');
    expect(eventIncomes[1].income).toEqual(2000);
    expect(eventIncomes[2].eventName).toEqual('Average');
    expect(eventIncomes[2].income).toEqual(1500);
  });

  it('getTicketsSoldByEvents() should query current service url', () => {
    this.chartService.getTicketsSoldByEvents();
    expect(this.lastConnection).toBeDefined('no http service connection at all');
    expect(this.lastConnection.url).toMatch( this.constantsService.chartsPath + '/event_tickets_sold', 'url invalid');
  });

  it('getTicketsSoldByEvents() should return a ChartIncomeEvents[]', () => {
    let eventTicketsSold: ChartTicketsSoldEvents[];
    this.chartService.getTicketsSoldByEvents().then((data: ChartTicketsSoldEvents[]) => eventTicketsSold = data);
    this.lastConnection.mockRespond(new HttpResponse({
      body: JSON.stringify([
        {
          eventName: 'Event1',
          ticketsSold: 10
        },
        {
          eventName: 'Event2',
          ticketsSold: 20
        },
        {
          eventName: 'Average',
          ticketsSold: 15
        },
      ])
    }));
    tick();
    expect(eventTicketsSold.length).toEqual(3, 'should contain given amount of elements');
    expect(eventTicketsSold[0].eventName).toEqual('Event1');
    expect(eventTicketsSold[0].ticketsSold).toEqual(10);
    expect(eventTicketsSold[1].eventName).toEqual('Event2');
    expect(eventTicketsSold[1].ticketsSold).toEqual(20);
    expect(eventTicketsSold[2].eventName).toEqual('Average');
    expect(eventTicketsSold[2].ticketsSold).toEqual(15);
  });

  it('getIncomeByLocations() should query current service url', () => {
    this.chartService.getIncomeByLocations();
    expect(this.lastConnection).toBeDefined('no http service connection at all');
    expect(this.lastConnection.url).toMatch( this.constantsService.chartsPath + '/event_tickets_sold', 'url invalid');
  });

  it('getIncomeByLocations() should return a ChartIncomeEvents[]', () => {
    let locationIncomes: ChartIncomeLocations[];
    this.chartService.getIncomeByLocations().then((data: ChartIncomeLocations[]) => locationIncomes = data);
    this.lastConnection.mockRespond(new HttpResponse({
      body: JSON.stringify([
        {
          locationName: 'Location1',
          income: 1000.00
        },
        {
          locationName: 'Location2',
          income: 2000.00
        },
        {
          locationName: 'Average',
          income: 1500.00
        },
      ])
    }));
    tick();
    expect(locationIncomes.length).toEqual(3, 'should contain given amount of elements');
    expect(locationIncomes[0].locationName).toEqual('Location1');
    expect(locationIncomes[0].income).toEqual(1000);
    expect(locationIncomes[1].locationName).toEqual('Location2');
    expect(locationIncomes[1].income).toEqual(2000);
    expect(locationIncomes[2].locationName).toEqual('Average');
    expect(locationIncomes[2].income).toEqual(1500);
  });

  it('getTicketsSoldByLocations() should query current service url', () => {
    this.chartService.getTicketsSoldByLocations();
    expect(this.lastConnection).toBeDefined('no http service connection at all');
    expect(this.lastConnection.url).toMatch( this.constantsService.chartsPath + '/event_tickets_sold', 'url invalid');
  });

  it('getTicketsSoldByLocations() should return a ChartIncomeEvents[]', () => {
    let locationTicketsSold: ChartTicketsSoldLocations[];
    this.chartService.getTicketsSoldByLocations().then((data: ChartTicketsSoldLocations[]) => locationTicketsSold = data);
    this.lastConnection.mockRespond(new HttpResponse({
      body: JSON.stringify([
        {
          locationName: 'Location1',
          ticketsSold: 100
        },
        {
          locationName: 'Location2',
          ticketsSold: 20
        },
        {
          locationName: 'Average',
          ticketsSold: 15
        },
      ])
    }));
    tick();
    expect(locationTicketsSold.length).toEqual(3, 'should contain given amount of elements');
    expect(locationTicketsSold[0].locationName).toEqual('Location1');
    expect(locationTicketsSold[0].ticketsSold).toEqual(1000);
    expect(locationTicketsSold[1].locationName).toEqual('Location2');
    expect(locationTicketsSold[1].ticketsSold).toEqual(2000);
    expect(locationTicketsSold[2].locationName).toEqual('Average');
    expect(locationTicketsSold[2].ticketsSold).toEqual(1500);
  });
});
