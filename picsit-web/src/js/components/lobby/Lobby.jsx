import React, {Component, Fragment} from "react";
import "./Lobby.scss";
import Backend from "../../Backend";
import PropTypes from "prop-types";

class Lobby extends Component {

    constructor() {
        super();

    }

    startGame() {
        Backend.startGame().then(() => {
            this.props.onChange()
        });
    }

    render() {
        const btnClassName = 'btn btn-start' + (this.props.players.length <= 1 ? ' disabled' : '');
        const players = this.props.players.map((player, index) => <li key={index}>{player}</li>);

        return (
            <Fragment>
                <div className={'margin-bottom-20'}>Spieler</div>
                <ul className={'list stretch'}>{players}</ul>
                {this.props.myGame ?
                    <div className={btnClassName} onClick={this.startGame.bind(this)}>Starte Spiel</div> : ''}
            </Fragment>
        );
    }
}

Lobby.propTypes = {
    onChange: PropTypes.func.isRequired,
    players: PropTypes.arrayOf(PropTypes.string).isRequired,
    myGame: PropTypes.bool.isRequired
};

export default Lobby;