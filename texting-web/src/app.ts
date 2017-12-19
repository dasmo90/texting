import {NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {Routes, RouterModule} from '@angular/router';
import {platformBrowserDynamic} from "@angular/platform-browser-dynamic";
import {MainApp} from "./components/main.app";
import {HeaderComponent} from "./components/header/header.component";
import {NamePage} from "./components/pages/name/name.page";
import {StartPage} from "./components/pages/start/start.page";

const routes: Routes = [
    {
        path: '',
        component: NamePage
    },
    {
        path: 'start',
        component: HeaderComponent
    }
];

@NgModule({
    imports: [
        BrowserModule,
        RouterModule.forRoot(routes),
    ],
    bootstrap: [MainApp],
    declarations: [
        MainApp,
        HeaderComponent,
        NamePage,
        StartPage
    ],
})
export class AppModule {

}

platformBrowserDynamic().bootstrapModule(AppModule);
