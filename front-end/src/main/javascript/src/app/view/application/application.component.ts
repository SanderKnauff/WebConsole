import {Component, OnInit, SecurityContext} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {LogLine} from "../../model/log-line";
import {LogType} from "../../model/log-type";
import {interval} from "rxjs/index";
import {HtmlSanitizer} from "../../service/html-sanitizer.service";

@Component({
  selector: 'app-application',
  templateUrl: './application.component.html',
  styleUrls: ['./application.component.scss']
})
export class ApplicationComponent implements OnInit {

  private applicationId: string;

  private messages: LogLine[] = [];

  constructor(private activatedRoute: ActivatedRoute, private htmlSanatizer: HtmlSanitizer) {
    this.activatedRoute.params.subscribe(params => this.applicationId = params.applicationId);
  }

  ngOnInit() {
    interval(1000)
      .subscribe((val) => {
        let line = "[0;33;1m1II1[39;0m[0;33;1m <h1>left</h1> the & game[39;0m[39;0mThis is a logLine with type ";
        line = this.htmlSanatizer.sanitize(line);
        this.messages.push(new LogLine(line + LogType[val % 3], val % 3));
      });
  }

}
