import React, {Component} from "react";
import "../games/Games.scss";
import PropTypes from "prop-types";
import Backend from "../../Backend";
import Lobby from "../lobby/Lobby.jsx";
import Cards from "../cards/Cards.jsx";

class Game extends Component {

    constructor() {
        super();
        this.state = {
            template: <div>Loading ...</div>
        }
    }

    componentDidMount() {
        this.updateGameStatus();
        this.interval = setInterval(() => {
            this.updateGameStatus();
        }, 2000)
    }

    updateGameStatus() {
        Backend.pollGameStatus().then(response => {
            const data = response.data;
            if (data.status === 0) {
                this.setState({
                    template: <Lobby onChange={() => {
                        this.updateGameStatus()
                    }} players={data.playerNames} myGame={data.myGame}/>
                });
                if (data.myGame) {
                    this.props.onNotificationChange('Starte das Spiel');
                } else {
                    this.props.onNotificationChange('Warte auf Start des Spiels ...');
                }
            } else if (data.status === 1) {
                this.setState({
                    template: <Cards onChange={() => {
                        this.updateGameStatus()
                    }} yourTurn={true} cardsOnHand={data.cardsOnHand}/>
                });
                if (data.yourTurn) {
                    this.props.onNotificationChange('Wähle eine Karte und einen Titel');
                } else  {
                    this.props.onNotificationChange('Warte auf ' + data.playerNames[data.whosTurnIndex] + ' ...');
                }
            } else {
                this.setState({template: <div>Loading ...</div>});
            }
        })
    }

    componentWillUnmount() {
        clearInterval(this.interval);
    }

    render() {
        return this.state.template;
    }
}

Game.propTypes = {
    onNotificationChange: PropTypes.func.isRequired
};

export default Game;