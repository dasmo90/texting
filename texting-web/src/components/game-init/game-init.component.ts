import {Component, Input, OnInit, Output, EventEmitter} from "@angular/core";
import {GameStatusDto} from "../../dto/game-status.dto";
import {GameService} from "../../service/game.service";
import {HttpClient} from "@angular/common/http";

@Component({
    selector: "game-init-component",
    styles: [
        require("./game-init.component.scss"),
    ],
    template: require("./game-init.component.html"),
})
export class GameInitComponent implements OnInit {

    private myGame: boolean;

    @Input()
    private gameStatus: GameStatusDto;

    @Output()
    private onStarted: EventEmitter<void>;

    @Output()
    private onLeave: EventEmitter<void>;

    constructor(private gameService: GameService, private httpClient: HttpClient) {
        this.onStarted = new EventEmitter<void>();
        this.onLeave = new EventEmitter<void>();
    }

    public ngOnInit(): void {
        this.myGame = this.gameService.isMyGame();
    }

    private startGame(): void {
        this.httpClient.get("game/start", {
            withCredentials: true
        }).subscribe((success: boolean) => {
            if (success === true) {
                this.onStarted.emit();
            }
        })
    }

    private leaveGame(): void {
        this.httpClient.get("game/leave", {
            withCredentials: true
        }).subscribe((success: boolean) => {
            if (success === true) {
                this.gameService.leaveGame();
                this.onLeave.emit();
            }
        });
    }

    private shutGame(): void {
        this.httpClient.get("game/shut", {
            withCredentials: true
        }).subscribe((success: boolean) => {
            if (success === true) {
                this.gameService.leaveGame();
                this.onLeave.emit();
            }
        });
    }
}
