import { Component, OnInit, Input, OnChanges } from '@angular/core';
import {SystemInformations } from '../model/systemInformations.model';
import { ToastrService } from 'ngx-toastr';
import {ChartService} from '../../core/services/chart.service';


@Component({
  selector: 'app-system-informations',
  templateUrl: './system-informations.component.html',
  styleUrls: ['./system-informations.component.scss']
})
export class SystemInformationsComponent implements OnInit {

  @Input()
  sysInfo: SystemInformations;

  renderedCh1 = true;

  constructor(private chartService: ChartService) {
    /*this.sysInfo = {
      allTimeIncome: 0,
      allTimeTickets: 0,
      numberOfAdmins: 0,
      numberOfEvents: 0,
      numberOfUsers: 0
    }*/
  }

  ngOnInit() {
  }

  renderSysInfo() {
    this.chartService.getSysInfo().subscribe(
      result => {
        console.log('Succesful sysinfo');
        console.log(result);
        this.sysInfo = result.body as SystemInformations;
        this.renderedCh1 = true;
      }
    );
  }

}
