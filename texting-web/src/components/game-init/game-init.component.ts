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
        // no action
    }

    private startGame(): void {
        this.httpClient.get("http://192.168.0.19:8080/game/start", {
            withCredentials: true
        }).subscribe((success: boolean) => {
            if (success === true) {
                this.onStarted.emit();
            }
        })
    }

    private leaveGame(): void {
        this.gameService.leaveGame();
        this.onLeave.emit();
    }
}
