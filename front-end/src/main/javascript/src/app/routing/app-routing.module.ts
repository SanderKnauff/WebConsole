import {RouterModule, Routes} from "@angular/router";
import {NgModule} from "@angular/core";
import {DashboardComponent} from "../views/dashboard/dashboard.component";
import {ApplicationComponent} from "../views/application/application.component";
import {LoginComponent} from "../views/login/login.component";
import {LoginGuard} from "../service/login/login.guard";

const routes: Routes = [
  {path: 'login', component: LoginComponent},
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
