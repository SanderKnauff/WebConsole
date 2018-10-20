import {Component, ElementRef, Input, OnInit, Renderer2} from '@angular/core';
import {LogLine} from "../../../model/log-line";


@Component({
  selector: 'app-application-console',
  templateUrl: './application-console.component.html',
  styleUrls: ['./application-console.component.scss']
})
export class ApplicationConsoleComponent {

  @Input()
  private messages: LogLine[];

  constructor() {
  }

}

