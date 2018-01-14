import {Component, OnInit, ViewChild} from "@angular/core";
import {GameService} from "../service/game.service";
import {DialogComponent} from "./dialog/dialog.component";

@Component({
    selector: "texting-app",
    styles: [
        require("./main.app.scss"),
    ],
    template: require("./main.app.html"),
})
export class MainApp implements OnInit {

    private status: number = 0;

    @ViewChild("dialog")
    private dialog: DialogComponent;

    constructor(private gameService: GameService) {
        // no action
    }

    public ngOnInit(): void {
        const player = this.gameService.getCurrentPlayer();
        if (player === null) {
            this.status = 0;
        } else {
            const gameId = this.gameService.getCurrentGameId();
            if (gameId === null) {
                this.status = 1;
            } else {
                this.status = 2;
            }
        }
    }
}
