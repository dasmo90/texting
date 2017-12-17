import {NgModule} from "@angular/core";
import {platformBrowserDynamic} from "@angular/platform-browser-dynamic";
import {BrowserModule} from "@angular/platform-browser";
import {AppComponent} from "./components/app/app.component";
import {HeaderBarComponent} from "./components/header-bar/header-bar.component";

@NgModule({
    imports:      [BrowserModule],
    bootstrap:    [AppComponent],
    declarations: [
        AppComponent,
        HeaderBarComponent
    ]
})
export class AppModule {}

platformBrowserDynamic().bootstrapModule(AppModule);
