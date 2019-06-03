import {Component} from '@angular/core';
import {LoginService} from "../../service/login/login.service";
import {LoginCredentials} from "../../model/login-credentials";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {

  constructor(private loginService: LoginService) {
  }

  login(credentials: any) {
    this.loginService.login(new LoginCredentials(credentials.username, credentials.password));
  }

}
