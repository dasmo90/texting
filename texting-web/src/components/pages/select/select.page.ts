import {Component, Input, OnInit, Output, EventEmitter, OnDestroy} from "@angular/core";
import {Player} from "../../../model/player.model";
import {GameService} from "../../../service/game.service";
import {Observable, Subject} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {GameTeaserDto} from "../../../dto/game-teaser.dto";
import {HttpParams} from "@angular/common/http";

@Component({
    selector: "select-page",
    styles: [
        require("./select.page.scss"),
    ],
    template: require("./select.page.html"),
})
export class SelectPage implements OnInit, OnDestroy {

    private player: Player;

    private games: GameTeaserDto [] = [];

    @Output()
    private onLogout: EventEmitter<void>;

    @Output()
    private onGameSelected: EventEmitter<void>;

    private unsubscribeSubject: Subject<GameTeaserDto[]>;

    constructor(private httpClient: HttpClient, private gameService: GameService) {
        this.onLogout = new EventEmitter<void>();
        this.onGameSelected = new EventEmitter<void>();
        this.unsubscribeSubject = new Subject<GameTeaserDto[]>();
    }

    public ngOnInit(): void {
        this.player = this.gameService.getCurrentPlayer();
        Observable.interval(2000)
            .startWith(0)
            .takeUntil(this.unsubscribeSubject)
            .switchMap(() => this.httpClient.get("games/unstarted/poll", {
                withCredentials: true,
            }))
            .subscribe((data: GameTeaserDto[]) => {
                this.games = data;
            });
    }

    public ngOnDestroy(): void {
        this.unsubscribeSubject.next();
        this.unsubscribeSubject.complete();
    }

    private newGame(): void {
        this.httpClient.get("game/new", {
            params: new HttpParams()
                .set("shownLetters", "40")
                .set("minLetters", "50")
                .set("maxLetters", "150")
                .set("rounds", "5"),
            withCredentials: true,
        })
            .subscribe((gameId: string) => {
                this.gameService.newGame(gameId);
                this.onGameSelected.emit();
            });
    }

    private enterGame(gameId: string): void {
        this.gameService.enterGame(gameId);
        this.httpClient.get("game/enter", {
            withCredentials: true,
        })
            .subscribe((success: boolean) => {
                if (success === true) {
                    this.onGameSelected.emit();
                } else {
                    this.gameService.leaveGame();
                }
            });
    }

    private logout(): void {
        this.gameService.logout();
        this.onLogout.emit();
    }
}
