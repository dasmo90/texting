import {Component, Input} from "@angular/core";
import {CookieUtil} from "../../../utils/cookie.util";

@Component({
    selector: "select-page",
    styles: [
        require("./select.page.scss"),
    ],
    template: require("./select.page.html"),
})
export class Selectpage {

    private name: string;

    constructor() {
        this.name = CookieUtil.getCookie("name");
    }
}
