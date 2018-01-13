import {HttpClient, HttpParams} from "@angular/common/http";
import {Component, EventEmitter, OnInit, Output} from "@angular/core";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Player} from "../../../model/player.model";
import {GameService} from "../../../service/game.service";

@Component({
    selector: "start-page",
    styles: [
        require("./start.page.scss"),
    ],
    template: require("./start.page.html"),
})
export class StartPage implements OnInit {

    private nameMaxLength: number = 15;

    private form: FormGroup;

    @Output()
    private onLogin: EventEmitter<Player>;

    constructor(formBuilder: FormBuilder, private httpClient: HttpClient, private gameService: GameService) {
        this.form = formBuilder.group({
            name: [null, Validators.compose([Validators.required, Validators.maxLength(this.nameMaxLength)])],
        });
        this.onLogin = new EventEmitter<Player>();
    }

    public ngOnInit(): void {
        // no onInit action
    }

    private next(value: any): void {
        if (this.form.valid) {
            let name = value.name.trim();
            this.httpClient.get("login", {
                params: new HttpParams().set("name", name),
            }).subscribe((result) => {
                let player = new Player(result.toString(), name);
                this.gameService.login(player);
                this.onLogin.emit(player);
            });
        }
    }
}
