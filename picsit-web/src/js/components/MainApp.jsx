import React, {Component, Fragment} from "react";
import Header from "./header/Header.jsx";
import Login from "./login/Login.jsx";
import Cookie from "../Cookie";
import Games from "./games/Games.jsx";
import Game from "./game/Game.jsx";

const COOKIE_USER_KEY = 'TEXTING-COOKIE-COMPANION-ID';
const COOKIE_GAME_KEY = 'TEXTING-COOKIE-GAME-ID';

class MainApp extends Component {

    constructor() {
        super();
        const userId = Cookie.get(COOKIE_USER_KEY);
        const gameId = Cookie.get(COOKIE_GAME_KEY);
        if (userId) {
            if (gameId) {
                this.state = {
                    template: <Game onNotificationChange={(notification) => this.setState({notification})}/>,
                    notification: ''
                }
            } else {
                this.state = {
                    template: <Games onSelect={this.onGameSelect.bind(this)}/>,
                    notification: 'Erstelle oder wähle ein Spiel'
                }
            }
        } else {
            this.state = {
                template: <Login onLogin={this.onLogin.bind(this)}/>,
                notification: 'Gib deinen Namen ein'
            }
        }
    }

    onLogin(response) {
        Cookie.set(COOKIE_USER_KEY, response.data);
        this.setState({
            template: <Games onSelect={this.onGameSelect.bind(this)}/>,
            notification: 'Erstelle oder wähle ein Spiel'
        });
    }

    onGameSelect(gameId) {
        Cookie.set(COOKIE_GAME_KEY, gameId);
        this.setState({
            template: <Game onNotificationChange={(notification) => this.setState({notification})}/>
        });
    }

    onLogout() {
        Cookie.clear(COOKIE_USER_KEY);
        Cookie.clear(COOKIE_GAME_KEY);
        this.setState({
            template: <Login onLogin={this.onLogin.bind(this)}/>,
            notification: 'Gib deinen Namen ein'
        });
    }

    render() {
        return <Fragment>
            <Header notification={this.state.notification} onLogout={this.onLogout.bind(this)}/>
            <div className={"content"}>{this.state.template}</div>
        </Fragment>
    }
}

export default MainApp;