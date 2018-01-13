import {HttpClient} from "@angular/common/http";
import {Component, EventEmitter, Input, OnInit, Output} from "@angular/core";
import {GameStatusDto} from "../../dto/game-status.dto";
import {GameService} from "../../service/game.service";

@Component({
    selector: "game-finished-component",
    styles: [
        require("./game-finished.component.scss"),
    ],
    template: require("./game-finished.component.html"),
})
export class GameFinishedComponent implements OnInit {

    @Input()
    private gameStatus: GameStatusDto;

    @Output()
    private onLeave: EventEmitter<void>;

    constructor(private gameService: GameService, private httpClient: HttpClient) {
        this.onLeave = new EventEmitter<void>();
    }

    public ngOnInit(): void {
        // no action
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
