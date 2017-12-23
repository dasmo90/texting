import {HttpClientModule} from "@angular/common/http";
import {NgModule} from "@angular/core";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {BrowserModule} from "@angular/platform-browser";
import {platformBrowserDynamic} from "@angular/platform-browser-dynamic";
import {HeaderComponent} from "./components/header/header.component";
import {MainApp} from "./components/main.app";
import {SelectPage} from "./components/pages/select/select.page";
import {StartPage} from "./components/pages/start/start.page";
import {GameService} from "./service/game.service";
import {GamePage} from "./components/pages/game/game.page";

@NgModule({
    imports: [
        BrowserModule,
        FormsModule,
        ReactiveFormsModule,
        HttpClientModule,
    ],
    providers: [
        GameService
    ],
    bootstrap: [
        MainApp
    ],
    declarations: [
        MainApp,
        HeaderComponent,
        SelectPage,
        StartPage,
        GamePage,
    ],
})
export class AppModule {

}

platformBrowserDynamic().bootstrapModule(AppModule);
