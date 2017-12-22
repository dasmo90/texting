import {HttpClientModule} from "@angular/common/http";
import {NgModule} from "@angular/core";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {BrowserModule} from "@angular/platform-browser";
import {platformBrowserDynamic} from "@angular/platform-browser-dynamic";
import {HeaderComponent} from "./components/header/header.component";
import {MainApp} from "./components/main.app";
import {SelectPage} from "./components/pages/select/select.page";
import {StartPage} from "./components/pages/start/start.page";

@NgModule({
    imports: [
        BrowserModule,
        FormsModule,
        ReactiveFormsModule,
        HttpClientModule,
    ],
    bootstrap: [MainApp],
    declarations: [
        MainApp,
        HeaderComponent,
        SelectPage,
        StartPage,
    ],
})
export class AppModule {

}

platformBrowserDynamic().bootstrapModule(AppModule);
