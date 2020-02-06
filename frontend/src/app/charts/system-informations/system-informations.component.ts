import { Component, OnInit, Input } from '@angular/core';
import {SystemInformations } from '../model/systemInformations.model';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-system-informations',
  templateUrl: './system-informations.component.html',
  styleUrls: ['./system-informations.component.scss']
})
export class SystemInformationsComponent implements OnInit {
  @Input()
  sysInfo: SystemInformations;

  constructor() {
  }

  ngOnInit() {
    this.sysInfo = new SystemInformations();
    this.sysInfo.numberOfUsers = 0;
    this.sysInfo.allTimeTickets = 0;
    this.sysInfo.allTimeIncome = 0;
    this.sysInfo.numberOfAdmins = 0;
    this.sysInfo.numberOfEvents = 0;
  }

}
