import {RouterModule, Routes} from "@angular/router";
import {NgModule} from "@angular/core";
import {DashboardComponent} from "../view/dashboard/dashboard.component";
import {ApplicationComponent} from "../view/application/application.component";
import {LoginComponent} from "../view/login/login.component";
import {LoginGuard} from "../service/login/login.guard";
import {SetupComponent} from "../view/setup/setup.component";

const routes: Routes = [
  {path: 'login', component: LoginComponent},
  {path: 'setup', component: SetupComponent},
  {path: '', component: DashboardComponent, canActivate: [LoginGuard]},
  {path: 'application/:applicationId', component: ApplicationComponent, canActivate: [LoginGuard]},
  {path: '**', redirectTo: ''}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
