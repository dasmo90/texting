import {Component, OnInit} from "@angular/core";
import {Router} from "@angular/router";
import {FormGroup, FormBuilder, Validators} from "@angular/forms";
import {CookieUtil} from "../../../utils/cookie.util";

@Component({
    selector: "start-page",
    styles: [
        require("./start.page.scss"),
    ],
    template: require("./start.page.html"),
})
export class StartPage implements OnInit {

    id: string;
    form: FormGroup;

    constructor(private router: Router, formBuilder: FormBuilder) {
        this.form = formBuilder.group({
            name: [null, Validators.compose([Validators.required, Validators.minLength(5), Validators.maxLength(15)])],
        })
    }

    ngOnInit(): void {
        let name = CookieUtil.getCookie("name");
        this.id = CookieUtil.getCookie("userId");
        if (name) {
            this.form.setValue({name: name});
        }
    }

    next(value: any): void {
        if (this.form.valid) {
            CookieUtil.setCookie("name", value.name);
            this.router.navigate(["select"]);
        }
    }
}
