import {Component, Input, OnInit} from "@angular/core";
import {CookieUtil} from "../utils/cookie.util";

@Component({
    selector: "texting-app",
    styles: [
        require("./main.app.scss"),
    ],
    template: require("./main.app.html"),
})
export class MainApp implements OnInit {

    private userName: string;
    private userId: string;

    private status: number = 0;

    public ngOnInit(): void {
        const userId = CookieUtil.getCookie("TEXTING-COOKIE-COMPANION-ID");
        console.log(userId);
        if (userId) {
            this.userName = CookieUtil.getCookie("TEXTING-COOKIE-COMPANION-NAME");
            this.userId = userId;
            this.status = 1;
        }
    }
}
