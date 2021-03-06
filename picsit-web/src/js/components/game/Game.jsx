import React, {Component} from "react";
import "../games/Games.scss";
import PropTypes from "prop-types";
import Backend from "../../Backend";
import Lobby from "../lobby/Lobby.jsx";
import Cards from "../cards/Cards.jsx";
import Middle from "../middle/Middle.jsx";
import Scores from "../scores/Scores.jsx";
import Cookie from "../../Cookie";

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
            this.props.onScoreChange(data.myScore);
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
            } else if (data.status === 1 && data.gamePhase === 0) {
                const latestTitle = Cookie.get('TEXTING-COOKIE-LATEST-TITLE');
                if (latestTitle) {
                    this.setState({
                        template: <Middle onChange={() => {
                            this.updateGameStatus()
                        }} waiting={true} cardsInMiddle={data.latestMiddleSituation} title={latestTitle}
                                          myCard={data.myLatestMiddleCard} myPickedCard={data.myLatestPickedCard}
                                          players={data.playerNames} myPoints={data.myLatestGainedScore}
                                          overview={true}/>
                    });
                    this.props.onNotificationChange('Übersicht - starte nächste Runde');
                } else {
                    const waitingForAll = data.myPhase !== 0 || data.yourTurn && !!data.title;
                    if (data.yourTurn && waitingForAll) {
                        this.setState({
                            template: <Middle onChange={() => {
                                this.updateGameStatus()
                            }} waiting={waitingForAll} cardsInMiddle={data.latestMiddleSituation} title={data.title}
                                              myCard={data.myLatestMiddleCard} myPickedCard={data.myLatestPickedCard}
                                              players={data.playerNames} myPoints={data.myLatestGainedScore}
                                              overview={false}/>
                        });
                    } else {
                        this.setState({
                            template: <Cards onChange={() => {
                                this.updateGameStatus()
                            }} yourTurn={data.yourTurn} cardsOnHand={data.cardsOnHand} title={data.title}
                                             waiting={waitingForAll}/>
                        });
                    }
                    if (waitingForAll) {
                        this.props.onNotificationChange('Warte auf andere Spieler ...');
                    } else if (data.yourTurn) {
                        this.props.onNotificationChange('Wähle eine Karte und einen Titel');
                    } else {
                        if (data.title) {
                            this.props.onNotificationChange('Wähle eine Karte');
                        } else {
                            this.props.onNotificationChange('Warte auf ' + data.playerNames[data.whosTurnIndex] + ' ...');
                        }
                    }
                }
            } else if (data.status === 1 && data.gamePhase === 1) {
                Cookie.set('TEXTING-COOKIE-LATEST-TITLE', data.title);
                const waiting = data.yourTurn || data.myPhase === 2;
                this.setState({
                    template: <Middle onChange={() => {
                        this.updateGameStatus()
                    }} waiting={waiting} cardsInMiddle={data.latestMiddleSituation} title={data.title}
                                      myCard={data.myLatestMiddleCard} myPickedCard={data.myLatestPickedCard}
                                      players={data.playerNames} myPoints={data.myLatestGainedScore} overview={false}/>
                });
                if (waiting) {
                    this.props.onNotificationChange('Warte auf andere Spieler ...');
                } else {
                    this.props.onNotificationChange('Wähle eine Karte');
                }
            } else if (data.status === 2) {
                this.setState({
                    template: <Scores onLeaveGame={this.props.onGameEnd} players={data.playerNames}
                                      scores={data.scores}/>
                });
                this.props.onNotificationChange('Spielende - Punkteübersicht');
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
    onNotificationChange: PropTypes.func.isRequired,
    onScoreChange: PropTypes.func.isRequired,
    onGameEnd: PropTypes.func.isRequired
};

export default Game;