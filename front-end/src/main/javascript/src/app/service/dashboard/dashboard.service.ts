import { Injectable } from '@angular/core';
import {Observable} from "rxjs/index";
import {Application} from "../../model/application";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class DashboardService {

  constructor(private httpClient: HttpClient) { }

  public getAvailableApplications(): Observable<Application[]> {
    return this.httpClient.get<Application[]>("/application-list");
  }
}
