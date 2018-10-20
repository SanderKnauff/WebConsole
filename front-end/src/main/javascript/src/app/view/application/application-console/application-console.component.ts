import {Component, ElementRef, Input, ViewChild} from '@angular/core';
import {LogLine} from "../../../model/log-line";
import {ApplicationService} from "../../../service/application/application.service";


@Component({
  selector: 'app-application-console',
  templateUrl: './application-console.component.html',
  styleUrls: ['./application-console.component.scss'],
  host: {
    "(click)": "focusOnInput()"
  }
})
export class ApplicationConsoleComponent {

  @ViewChild("commandInput")
  private commandInputField: ElementRef;
  @Input()
  private messages: LogLine[];
  @Input()
  private applicationId: string;

  private command: string = '';

  constructor(private applicationService: ApplicationService) {
  }

  private sendCommand(event: KeyboardEvent): void {
    this.commandInputField.nativeElement.focus();
    if (event.key === 'Enter') {
      console.log(`Sending Command ${this.command}`);
      this.applicationService.sendCommand(this.applicationId, this.command);
      this.command = '';
    }
  }

  private focusOnInput(): void {
    this.commandInputField.nativeElement.focus({preventScroll: true});
  }
}

