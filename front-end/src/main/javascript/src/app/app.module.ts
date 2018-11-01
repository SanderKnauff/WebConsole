import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {AppComponent} from './app.component';
import {DashboardComponent} from './view/dashboard/dashboard.component';
import {AppRoutingModule} from "./routing/app-routing.module";
import {HttpClientModule} from "@angular/common/http";
import {FormsModule} from "@angular/forms";
import {ApplicationTileComponent} from './view/dashboard/application-tile/application-tile.component';
import {ApplicationComponent} from './view/application/application.component';
import {
  ApplicationNavigationButtonComponent,
  ApplicationNavigationComponent
} from './view/application/application-navigation/application-navigation.component';
import {ApplicationConsoleComponent} from './view/application/application-console/application-console.component';
import {LoginComponent} from "./view/login/login.component";
import {LoginGuard} from "./service/login/login.guard";
import {LoginService} from "./service/login/login.service";
import {ApplicationService} from "./service/application/application.service";
import {EscapeHtmlPipe} from "./pipe/keep-html";
import {HtmlSanitizer} from "./service/html-sanitizer.service";
import {DashboardService} from "./service/dashboard/dashboard.service";
import {StompConfig, StompService} from "@stomp/ng2-stompjs";

const stompConfig: StompConfig = {
  url: `ws://${window.location.host}/socket`,
  headers: {
  },
  heartbeat_in: 0,
  heartbeat_out: 20000,
  reconnect_delay: 5000,
  debug: false
};

@NgModule({
  declarations: [
    AppComponent,
    DashboardComponent,
    EscapeHtmlPipe,
    LoginComponent,
    ApplicationTileComponent,
    ApplicationComponent,
    ApplicationNavigationComponent,
    ApplicationNavigationButtonComponent,
    ApplicationConsoleComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [LoginGuard, LoginService, ApplicationService, HtmlSanitizer, DashboardService, StompService, {provide: StompConfig, useValue: stompConfig}],
  bootstrap: [AppComponent]
})
export class AppModule {
}
