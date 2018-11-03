import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {LogLine} from "../../model/log-line";
import {HtmlSanitizer} from "../../service/html-sanitizer.service";
import {ApplicationService} from "../../service/application/application.service";

@Component({
  selector: 'app-application',
  templateUrl: './application.component.html',
  styleUrls: ['./application.component.scss']
})
export class ApplicationComponent implements OnInit {

  private applicationId: string;

  private messages: LogLine[] = [];

  constructor(private activatedRoute: ActivatedRoute,
              private htmlSanitizer: HtmlSanitizer,
              private applicationService: ApplicationService) {
    this.activatedRoute.params.subscribe(params => this.applicationId = params.applicationId);
  }

  ngOnInit() {
    this.applicationService.openWebSocketConnection(this.applicationId).subscribe(
      (next) => {
        this.appendLog(this.messageBodyToLogLine(next.body));
      },
      (error) => console.log(`[${this.applicationId}] Received error: ${JSON.stringify(error)}`),
      () => console.log(`[${this.applicationId}] WebSocketConnection closed`)
    );
  }

  private appendLog(logLines: LogLine[]): void {
    for (let logLine of logLines) {
      let escapedLine: string = this.htmlSanitizer.sanitize(logLine.line);
      this.messages.push(new LogLine(escapedLine, logLine.logType));
      this.messages = Object.assign([], this.messages);
    }
  }

  handleCommand(command: string): void {
    this.applicationService.sendCommand(this.applicationId, command);
  }

  private messageBodyToLogLine(messageBody: string): LogLine[] {
    let rawLogLinesArray: any[] = JSON.parse(messageBody);
    let logLines: LogLine[] = [];
    for (let rawLogLine of rawLogLinesArray) {
      logLines.push(new LogLine(rawLogLine.line, rawLogLine.logType));
    }
    return logLines;
  }

}
