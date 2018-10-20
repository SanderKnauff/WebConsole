import { Component, OnInit } from '@angular/core';
import {LogLine} from "../../model/log-line";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-application',
  templateUrl: './application.component.html',
  styleUrls: ['./application.component.scss']
})
export class ApplicationComponent implements OnInit {

  private messages: LogLine[];

  private applicationId: string;

  constructor(private activatedRoute: ActivatedRoute) {
    this.activatedRoute.params.subscribe(params => this.applicationId = params.applicationId);
  }

  ngOnInit() {
  }

}
