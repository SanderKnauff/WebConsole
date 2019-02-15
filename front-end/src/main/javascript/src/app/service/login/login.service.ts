import {Injectable, isDevMode} from '@angular/core';
import {Router} from '@angular/router';
import {BehaviorSubject, Observable} from 'rxjs';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {User} from "../../model/user";
import {LoginCredentials} from "../../model/login-credentials";
import {Endpoints} from "../../model/endpoints";

@Injectable()
export class LoginService {

  private static readonly HEADER_URL_FORM_UNENCODED = new HttpHeaders().set('Content-Type', 'application/x-www-form-urlencoded')

  private loggedInUser: BehaviorSubject<User> = new BehaviorSubject<User>(null);

  constructor(private router: Router, private http: HttpClient) {
  }

  public login(credentials: LoginCredentials): void {
    this.authenticate(credentials);
  }

  public logout(): void {
    this.loggedInUser.next(null);
    this.router.navigate(['/login']);
  }

  public getUser(): Observable<User> {
    return this.loggedInUser.asObservable();
  }

  private authenticate(credentials: LoginCredentials): void {
    this.http
      .post<User>(Endpoints.LOGIN, `username=${credentials.username}&password=${credentials.password}`, {headers: LoginService.HEADER_URL_FORM_UNENCODED})
      .subscribe(
        user => this.handleLoginSuccess(user),
        error => this.handleLoginError(error)
      );
  }

  private handleLoginSuccess(user: User): void {
    this.loggedInUser.next(user);
    this.router.navigate(['/']);
  }

  private handleLoginError(error: any): void {
    console.log(error);
  }
}
