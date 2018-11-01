import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {StompService} from "@stomp/ng2-stompjs";
import {Observable} from "rxjs/index";
import {Message} from '@stomp/stompjs';

@Injectable()
export class ApplicationService {

  constructor(private http: HttpClient, private stompService: StompService) {
  }

  public startServer(applicationId: string, debugModeEnabled: boolean): void {
    this.http.get(`/application/${applicationId}/start?debug=${debugModeEnabled}`).subscribe();
  }

  public stopServer(applicationId: string, shouldKill: boolean): void {
    this.http.get(`/application/${applicationId}/stop?debug=${shouldKill}`).subscribe();
  }

  public restartServer(applicationId: string, debugModeEnabled: boolean): void {
    this.http.get(`/application/${applicationId}/restart?debug=${debugModeEnabled}`).subscribe();
  }

  public sendCommand(applicationId: string, command: string): void {
    this.http.post(`/application/${applicationId}/sendCommand`, command).subscribe()
  }

  public openWebSocketConnection(applicationId: string): Observable<Message> {
    this.stompService.state
      .subscribe((status: any) => {
        console.log(`Stomp connection status: ${status}`);
      });
    return this.stompService.subscribe("/logs/" + applicationId);
  }

}
