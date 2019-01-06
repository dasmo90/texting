import React, {Component, Fragment} from "react";
import "./Games.scss";
import Backend from "../../Backend.js";
import PropTypes from "prop-types";
import Cookie from "../../Cookie";

class Games extends Component {

    constructor() {
        super();
        this.state = {
            games: []
        }
    }

    newGame() {
        Backend.newGame().then(response => {
            this.props.onSelect(response.data)
        });
    }

    enterGame(gameId) {
        Cookie.set('TEXTING-COOKIE-GAME-ID', gameId);
        Backend.enterGame().then(() => {
            this.props.onSelect(gameId)
        })
    }

    componentDidMount() {
        Backend.pollAvailableGames().then(response => {
            this.setState({games: response.data})
        })
        this.interval = setInterval(() => {
            Backend.pollAvailableGames().then(response => {
                this.setState({games: response.data})
            })
        }, 2000)
    }

    componentWillUnmount() {
        clearInterval(this.interval);
    }

    render() {
        const games = this.state.games.map((game, index) =>
            <li key={index} onClick={() => this.enterGame(game.id)}>
                {game.name}<span className={'float-right'}>{game.players}</span>
            </li>)
        return (
            <Fragment>
                <div className={'margin-bottom-20'}>Verfügbare Spiele</div>
                {games.length > 0 ? <ul className={'list stretch clickable'}>{games}</ul> :
                    <div className={'stretch'}>Aktuell sind keine Spiele verfügbar</div>}
                <div className={'btn btn-add'} onClick={this.newGame.bind(this)}>Erstelle ein neues
                    Spiel
                </div>
            </Fragment>
        );
    }
}

Games.propTypes = {
    onSelect: PropTypes.func.isRequired
};

export default Games;