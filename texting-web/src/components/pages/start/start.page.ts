import {HttpClient, HttpParams} from "@angular/common/http";
import {Component, EventEmitter, OnInit, Output} from "@angular/core";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {CookieUtil} from "../../../utils/cookie.util";

@Component({
    selector: "start-page",
    styles: [
        require("./start.page.scss"),
    ],
    template: require("./start.page.html"),
})
export class StartPage implements OnInit {

    private form: FormGroup;

    @Output()
    private onLogin: EventEmitter<void>;

    constructor(formBuilder: FormBuilder, private httpClient: HttpClient) {
        this.form = formBuilder.group({
            name: [null, Validators.compose([Validators.required, Validators.minLength(5), Validators.maxLength(15)])],
        });
        this.onLogin = new EventEmitter<void>();
    }

    public ngOnInit(): void {
        // no onInit action
    }

    private next(value: any): void {
        if (this.form.valid) {
            this.httpClient.get("http://localhost:8080/login", {
                params: new HttpParams().set("name", value.name),
            }).subscribe((result) => {
                CookieUtil.setCookie("TEXTING-COOKIE-COMPANION-NAME", value.name);
                CookieUtil.setCookie("TEXTING-COOKIE-COMPANION-ID", result.toString());
                this.onLogin.emit();
            }, (error) => {
                console.log(error);
            });
        }
    }
}
