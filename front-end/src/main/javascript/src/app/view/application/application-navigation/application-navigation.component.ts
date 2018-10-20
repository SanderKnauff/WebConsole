import {Component, ElementRef, HostListener, Input, OnInit, Renderer2} from '@angular/core';
import {ApplicationService} from "../../../service/application/application.service";
import {LogLine} from "../../../model/log-line";

@Component({
  selector: 'app-application-navigation',
  templateUrl: './application-navigation.component.html',
  styleUrls: ['./application-navigation.component.scss']
})
export class ApplicationNavigationComponent implements OnInit {

  @Input()
  private applicationId: string;
  @Input()
  private messages: LogLine[];

  constructor(private applicationService: ApplicationService) {
  }

  ngOnInit() {
  }

  private startApplication(event: MouseEvent) {
    this.applicationService.startServer(this.applicationId, event.shiftKey);
  }

  private stopApplication(event: MouseEvent) {
    this.applicationService.stopServer(this.applicationId, event.shiftKey);
  }

  private restartApplication(event: MouseEvent) {
    this.applicationService.restartServer(this.applicationId, event.shiftKey);
  }

  private clearLogs() {
    this.messages = this.messages.splice(0, this.messages.length);
  }


}

@Component({
  selector: 'app-application-navigation-button',
  template: ''
})
export class ApplicationNavigationButtonComponent implements OnInit {

  @Input()
  private action: (event: Event) => void;
  @Input()
  private imageUrl: string;
  @Input()
  private altButton: boolean = false;

  constructor(private renderer: Renderer2, private elRef: ElementRef) {
  }

  ngOnInit() {
    this.renderer.setStyle(this.elRef.nativeElement, "background-image", `url('${this.imageUrl}')`);
  }

  @HostListener('window:keydown', ['$event'])
  private handleShiftPressed(event: KeyboardEvent) {
    if (event.shiftKey && this.altButton) {
      this.renderer.addClass(this.elRef.nativeElement, "altButtonActive");
    }
  }

  @HostListener('window:keyup', ['$event'])
  private handleShiftReleased(event: KeyboardEvent) {
    if (event.shiftKey && this.altButton) {
      this.renderer.removeClass(this.elRef.nativeElement, "altButtonActive");
    }
  }
}
