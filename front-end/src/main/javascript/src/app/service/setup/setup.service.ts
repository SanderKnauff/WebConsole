import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from "@angular/common/http";
import {NewUserDetails} from "../../model/new-user-details";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class SetupService {

  constructor(private http: HttpClient) {
  }

  public setup(userDetails: NewUserDetails): Observable<HttpResponse<void>> {
    return this.http.post<void>("/setup", userDetails, {observe: 'response'});
  }


}
