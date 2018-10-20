import {Component} from '@angular/core';
import {LoginService} from "../../service/login/login.service";
import {LoginCredentials} from "../../model/login-credentials";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {

  private credentials: LoginCredentials = new LoginCredentials(null, null);

  constructor(private loginService: LoginService) {
  }

  login() {
    this.loginService.login(this.credentials);
  }

}
