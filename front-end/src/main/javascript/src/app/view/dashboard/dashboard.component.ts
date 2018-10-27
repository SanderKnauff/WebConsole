import {Component, OnInit} from '@angular/core';
import {Application} from "../../model/application";
import {DashboardService} from "../../service/dashboard/dashboard.service";
import {Observable} from "rxjs/index";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  private applications: Observable<Application[]>;

  constructor(private dashboardService: DashboardService) {
  }

  ngOnInit() {
    this.applications = this.dashboardService.getAvailableApplications();
  }
}
