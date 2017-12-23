import {HttpClient, HttpParams} from "@angular/common/http";
import {Component, EventEmitter, OnInit, Output} from "@angular/core";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {GameService} from "../../../service/game.service";
import {Player} from "../../../model/player.model";

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
    private onLogin: EventEmitter<Player>;

    constructor(formBuilder: FormBuilder, private httpClient: HttpClient, private gameService: GameService) {
        this.form = formBuilder.group({
            name: [null, Validators.compose([Validators.required, Validators.minLength(5), Validators.maxLength(15)])],
        });
        this.onLogin = new EventEmitter<Player>();
    }

    public ngOnInit(): void {
        // no onInit action
    }

    private next(value: any): void {
        if (this.form.valid) {
            this.httpClient.get("http://localhost:8080/login", {
                params: new HttpParams().set("name", value.name),
            }).subscribe((result) => {
                let player = new Player(result.toString(), value.name);
                this.gameService.login(player);
                this.onLogin.emit(player);
            });
        }
    }
}
