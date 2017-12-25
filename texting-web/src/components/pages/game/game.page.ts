import {HttpClient, HttpParams} from "@angular/common/http";
import {Component, EventEmitter, OnInit, Output, OnDestroy} from "@angular/core";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {GameService} from "../../../service/game.service";
import {Player} from "../../../model/player.model";
import {Subject, Observable} from "rxjs";
import {GameStatusDto} from "../../../dto/game-status.dto";

@Component({
    selector: "game-page",
    styles: [
        require("./game.page.scss"),
    ],
    template: require("./game.page.html"),
})
export class GamePage implements OnInit, OnDestroy {

    private form: FormGroup;

    @Output()
    private onLogin: EventEmitter<Player>;

    @Output()
    private onLogout: EventEmitter<void>;

    private unsubscribeSubject: Subject<GameStatusDto>;

    private gameStatus: GameStatusDto;

    constructor(formBuilder: FormBuilder, private httpClient: HttpClient, private gameService: GameService) {
        this.form = formBuilder.group({
            text: [null, Validators.compose([Validators.required, Validators.minLength(50), Validators.maxLength(100)])],
        });
        this.onLogin = new EventEmitter<Player>();
        this.onLogout = new EventEmitter<void>();
        this.unsubscribeSubject = new Subject<GameStatusDto>();
    }

    public ngOnInit(): void {
        Observable.interval(2000)
            .startWith(0)
            .takeUntil(this.unsubscribeSubject)
            .switchMap(() => this.httpClient.get("http://localhost:8080/game/status/poll", {
                withCredentials: true,
            }))
            .subscribe((data: GameStatusDto) => {
                this.gameStatus = data;
            });
    }

    private update(): void {
        this.httpClient.get("http://localhost:8080/game/status/poll", {
            withCredentials: true,
        }).subscribe((data: GameStatusDto) => {
            this.gameStatus = data;
        });
    }

    private logout(): void {
        this.gameService.logout();
        this.onLogout.emit();
    }

    public ngOnDestroy(): void {
        this.unsubscribeSubject.next();
        this.unsubscribeSubject.complete();
    }
}
