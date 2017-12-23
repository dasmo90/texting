import {Injectable} from "@angular/core";
import {Player} from "../model/player.model";
import {CookieUtil} from "../utils/cookie.util";

@Injectable()
export class GameService {

    constructor() {
        console.log("blablabla");
    }

    public login(player: Player) {
        CookieUtil.setCookie("TEXTING-COOKIE-COMPANION-NAME", player.getName());
        CookieUtil.setCookie("TEXTING-COOKIE-COMPANION-ID", player.getId());
    }

    public getCurrentPlayer(): Player {
        const name = CookieUtil.getCookie("TEXTING-COOKIE-COMPANION-NAME");
        const id = CookieUtil.getCookie("TEXTING-COOKIE-COMPANION-ID");
        if (id && name) {
            return new Player(id, name);
        }
        CookieUtil.deleteCookie("TEXTING-COOKIE-COMPANION-NAME");
        CookieUtil.deleteCookie("TEXTING-COOKIE-COMPANION-ID");
        return null;
    }

    public logout() {
        CookieUtil.deleteCookie("TEXTING-COOKIE-COMPANION-NAME");
        CookieUtil.deleteCookie("TEXTING-COOKIE-COMPANION-ID");
    }

    public newGame(gameId: string): void {
        CookieUtil.setCookie("TEXTING-COOKIE-GAME-ID", gameId);
    }

    public getCurrentGameId(): string {
        const id = CookieUtil.getCookie("TEXTING-COOKIE-GAME-ID");
        if (id) {
            return id;
        }
        CookieUtil.deleteCookie("TEXTING-COOKIE-GAME-ID");
        return null;
    }
}