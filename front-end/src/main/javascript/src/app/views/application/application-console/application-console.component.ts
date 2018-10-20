import {Component, OnInit} from '@angular/core';
import {interval} from "rxjs";
import {LogLine} from "../../../model/log-line";
import {LogType} from "../../../model/log-type";

@Component({
  selector: 'app-application-console',
  templateUrl: './application-console.component.html',
  styleUrls: ['./application-console.component.scss']
})
export class ApplicationConsoleComponent implements OnInit {

  private messages: LogLine[] = [];

  constructor() {
  }

  ngOnInit() {
    interval(1000)
      .subscribe((val) => {
        this.messages.push(new LogLine("This is a logLine with type " + LogType[val % 3], val % 3));
      });
  }

  public clearMessages(): void {
    this.messages = [];
  }

}
