import {Component, OnInit} from '@angular/core';
import {delay, timeout} from "q";
import {Router} from "@angular/router";

@Component({
  selector: 'app-create-application',
  templateUrl: './create-application.component.html',
  styleUrls: ['./create-application.component.scss']
})
export class CreateApplicationComponent {

  private blocked: boolean = false;

  constructor(private router: Router) {
  }

  private createApplication(value: any) {
    this.blocked = true;
    console.log(value);
    delay(1000).then(() => {
      this.blocked = false;
    });
  }

  private navigateToDashboard() {
    this.router.navigate(["/"]);
  }
}
