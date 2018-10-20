import {Injectable, isDevMode} from '@angular/core';
import {Router} from '@angular/router';
import {BehaviorSubject} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {cr} from "@angular/core/src/render3";
import {User} from "../../model/user";
import {LoginCredentials} from "../../model/login-credentials";
import {Endpoints} from "../../model/endpoints";

@Injectable()
export class LoginService {
  private loggedInUser: BehaviorSubject<User> = new BehaviorSubject<User>(null);

  constructor(private router: Router, private http: HttpClient) {
  }

  getUser() {
    return this.loggedInUser.asObservable();
  }

  login(credentials: LoginCredentials) {
    if(!isDevMode()) {
      this.authenticate(credentials);
    } else {
      this.mockAuthenticate(credentials);
    }
  }

  private authenticate(credentials: LoginCredentials) {
    this.http
      .post<User>(Endpoints.LOGIN, credentials)
      .subscribe(
        user => this.handleLoginSuccess(user),
        error => this.handleLoginError(error)
      );
  }

  private mockAuthenticate(credentials: LoginCredentials) {
    if (credentials.username === 'test' && credentials.password === 'test') {
      this.handleLoginSuccess(<User>{})
    } else {
      this.handleLoginError("Wrong test credentials");
    }
  }

  logout() {
    this.loggedInUser.next(null);
    this.router.navigate(['/login']);
  }

  handleLoginSuccess(user: User) {
    this.loggedInUser.next(user);
    this.router.navigate(['/']);
    console.log(user);
  }

  handleLoginError(error: any) {
    console.log(error);
  }
}
