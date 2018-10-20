import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {WebSocketSubject} from "rxjs/webSocket";

@Injectable()
export class ApplicationService {

  constructor(private http: HttpClient) {
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

  public openWebSocketConnection(applicationId: string): WebSocketSubject<string> {
    return new WebSocketSubject("/socket");
  }

}
