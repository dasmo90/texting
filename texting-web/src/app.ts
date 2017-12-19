import {NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {Routes, RouterModule} from '@angular/router';
import {platformBrowserDynamic} from "@angular/platform-browser-dynamic";
import {MainApp} from "./components/main.app";
import {HeaderComponent} from "./components/header/header.component";
import {Selectpage} from "./components/pages/select/select.page";
import {StartPage} from "./components/pages/start/start.page";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";

const routes: Routes = [
    {
        path: "",
        component: StartPage,
    },
    {
        path: "select",
        component: Selectpage,
    }
];

@NgModule({
    imports: [
        BrowserModule,
        RouterModule.forRoot(routes),
        FormsModule,
        ReactiveFormsModule,
    ],
    bootstrap: [MainApp],
    declarations: [
        MainApp,
        HeaderComponent,
        Selectpage,
        StartPage,
    ],
})
export class AppModule {

}

platformBrowserDynamic().bootstrapModule(AppModule);
