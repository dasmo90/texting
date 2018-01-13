import {HttpClientModule} from "@angular/common/http";
import {NgModule} from "@angular/core";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {BrowserModule} from "@angular/platform-browser";
import {platformBrowserDynamic} from "@angular/platform-browser-dynamic";
import {GameFinishedComponent} from "./components/game-finished/game-finished.component";
import {GameInitComponent} from "./components/game-init/game-init.component";
import {GameRunningComponent} from "./components/game-running/game-running.component";
import {HeaderComponent} from "./components/header/header.component";
import {MainApp} from "./components/main.app";
import {GamePage} from "./components/pages/game/game.page";
import {SelectPage} from "./components/pages/select/select.page";
import {StartPage} from "./components/pages/start/start.page";
import {GameService} from "./service/game.service";

@NgModule({
    bootstrap: [
        MainApp,
    ],
    declarations: [
        MainApp,
        HeaderComponent,
        SelectPage,
        StartPage,
        GamePage,
        GameInitComponent,
        GameRunningComponent,
        GameFinishedComponent,
    ],
    imports: [
        BrowserModule,
        FormsModule,
        ReactiveFormsModule,
        HttpClientModule,
    ],
    providers: [
        GameService,
    ],
})
export class AppModule {

}

platformBrowserDynamic().bootstrapModule(AppModule);
