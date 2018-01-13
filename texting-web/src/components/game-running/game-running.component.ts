import {HttpClient} from "@angular/common/http";
import {HttpParams} from "@angular/common/http";
import {Component, EventEmitter, Input, OnInit, Output} from "@angular/core";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {GameStatusDto} from "../../dto/game-status.dto";
import {GameService} from "../../service/game.service";

@Component({
    selector: "game-running-component",
    styles: [
        require("./game-running.component.scss"),
    ],
    template: require("./game-running.component.html"),
})
export class GameRunningComponent implements OnInit {

    private form: FormGroup;

    @Input()
    private gameStatus: GameStatusDto;

    @Output()
    private onCommited: EventEmitter<void>;

    @Output()
    private onLeave: EventEmitter<void>;

    constructor(private formBuilder: FormBuilder, private gameService: GameService, private httpClient: HttpClient) {
        this.onCommited = new EventEmitter<void>();
        this.onLeave = new EventEmitter<void>();
    }

    public ngOnInit(): void {
        this.form = this.formBuilder.group({
            text: [null, Validators.compose([
                Validators.required,
                Validators.minLength(this.gameStatus.minLetters),
                Validators.maxLength(this.gameStatus.maxLetters),
            ])],
        });
    }

    private commit(value: any): void {
        if (this.form.valid) {
            this.httpClient.get("game/commit/storypiece", {
                params: new HttpParams().set("storypiece", value.text),
                withCredentials: true,
            }).subscribe((success: boolean) => {
                if (success === true) {
                    this.onCommited.emit();
                }
            });
        }
    }

    private leaveGame(): void {
        this.httpClient.get("game/leave", {
            withCredentials: true,
        }).subscribe((success: boolean) => {
            if (success === true) {
                this.gameService.leaveGame();
                this.onLeave.emit();
            }
        });
    }
}
