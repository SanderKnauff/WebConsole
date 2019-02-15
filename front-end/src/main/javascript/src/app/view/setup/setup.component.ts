import {Component} from '@angular/core';
import {NewUserDetails} from "../../model/new-user-details";
import {SetupService} from "../../service/setup/setup.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-setup',
  templateUrl: './setup.component.html',
  styleUrls: ['./setup.component.scss']
})
export class SetupComponent {

  private newUser: NewUserDetails = new NewUserDetails(null, null, null);

  constructor(private setupService: SetupService, private router: Router) {
  }

  public setup(): void {
    this.setupService.setup(this.newUser).subscribe(response => {
      switch (response.status) {
        case 200:
          this.openLoginPage();
          break;
        case 409:
          this.notifyAlreadySetup();
          break;
        default:
          this.notifyOtherError(response.status);
          break;
      }
    });
  }

  private openLoginPage(): void {
    this.router.navigate(["/login"])
  }

  private notifyAlreadySetup(): void {
    console.log(`Setup denied as the system is already in an initialized state`);
    this.router.navigate(["/login"])
  }

  private notifyOtherError(status: number): void {
    console.log(`Initial Application Set-up failed. Response: ${status}`);
  }


}
