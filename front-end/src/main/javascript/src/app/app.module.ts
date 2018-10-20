import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {DashboardComponent} from './views/dashboard/dashboard.component';
import {AppRoutingModule} from "./routing/app-routing.module";
import {HttpClientModule} from "@angular/common/http";
import {FormsModule} from "@angular/forms";
import { ApplicationTileComponent } from './views/dashboard/application-tile/application-tile.component';
import { ApplicationComponent } from './views/application/application.component';
import { ApplicationNavigationComponent } from './views/application/application-navigation/application-navigation.component';
import { ApplicationConsoleComponent } from './views/application/application-console/application-console.component';
import {LoginComponent} from "./views/login/login.component";
import {LoginGuard} from "./service/login/login.guard";
import {LoginService} from "./service/login/login.service";

@NgModule({
  declarations: [
    AppComponent,
    DashboardComponent,
    LoginComponent,
    ApplicationTileComponent,
    ApplicationComponent,
    ApplicationNavigationComponent,
    ApplicationConsoleComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [LoginGuard, LoginService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
