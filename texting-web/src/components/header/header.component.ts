import {Component, Input} from "@angular/core";

@Component({
    selector: "header-component",
    styles: [
        require("./header.component.scss"),
    ],
    template: require("./header.component.html"),
})
export class HeaderComponent {

    @Input()
    private title: string;
}
