import {ChangeDetectionStrategy, Component, ElementRef, Input, ViewChild} from '@angular/core';
import {LogLine} from "../../../model/log-line";
import {ApplicationService} from "../../../service/application/application.service";
import {FormControl, FormGroup} from "@angular/forms";


@Component({
  selector: 'app-application-console',
  templateUrl: './application-console.component.html',
  styleUrls: ['./application-console.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
  host: {
    "(dblclick)": "focusOnInput()"
  }
})
export class ApplicationConsoleComponent {

  @ViewChild("commandInput")
  private commandInputField: ElementRef;
  @Input()
  private messages: LogLine[];
  @Input()
  private applicationId: string;

  private commandGroup: FormGroup = new FormGroup({
    command: new FormControl()
  });

  constructor(private applicationService: ApplicationService) {
  }

  private sendCommand(form: any): void {
    this.applicationService.sendCommand(this.applicationId, form.command);
    this.commandGroup.reset();
  }

  private focusOnInput(): void {
    this.commandInputField.nativeElement.focus({preventScroll: false});
  }
}

