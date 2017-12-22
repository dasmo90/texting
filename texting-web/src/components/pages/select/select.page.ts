import {Component, Input, OnInit} from "@angular/core";
import {CookieUtil} from "../../../utils/cookie.util";

@Component({
    selector: "select-page",
    styles: [
        require("./select.page.scss"),
    ],
    template: require("./select.page.html"),
})
export class SelectPage implements OnInit {

    private name: string;

    public ngOnInit(): void {
        this.name = CookieUtil.getCookie("name");
    }
}
