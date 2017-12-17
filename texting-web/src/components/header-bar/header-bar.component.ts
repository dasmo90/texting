import {Component, Input} from "@angular/core";

@Component({
    selector: "header-bar",
    styles: [
        require("./header-bar.component.scss")
    ],
    template: require("./header-bar.component.html")
})
export class HeaderBarComponent {

    @Input()
    private title: string;
}