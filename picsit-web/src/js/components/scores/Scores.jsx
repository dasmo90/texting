import React, {Component, Fragment} from "react";
import "./Scores.scss";
import Backend from "../../Backend";
import PropTypes from "prop-types";

class Scores extends Component {

    constructor() {
        super();

    }

    render() {
        const players = this.props.players.map((player, index) => <li key={index}>{player}<span
            className={'float-right'}>{this.props.scores[index]}</span></li>);

        return (
            <Fragment>
                <div className={'margin-bottom-20'}>Spieler</div>
                <ul className={'list stretch margin-bottom-20'}>{players}</ul>
                <div className={'btn'} onClick={this.props.onLeaveGame}>Spiel verlassen</div>
            </Fragment>
        );
    }
}

Scores.propTypes = {
    onLeaveGame: PropTypes.func.isRequired,
    players: PropTypes.arrayOf(PropTypes.string).isRequired,
    scores: PropTypes.arrayOf(PropTypes.number).isRequired
};

export default Scores;