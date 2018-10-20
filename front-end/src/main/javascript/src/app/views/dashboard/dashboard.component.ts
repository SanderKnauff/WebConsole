import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {Application} from "../../model/application";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  private applications = [
    new Application("id1", "TestApplication", 90),
    new Application("id2", "OtherApplication", 180),
    new Application("id3", "SoMuchApplications", 270),
    new Application("id4", "TheFunNeverEnds", 360),
  ];

  constructor() {
  }

  ngOnInit() {
  }
}
