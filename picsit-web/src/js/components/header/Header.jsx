import React, {Component, Fragment} from "react";
import "./Header.scss";
import PropTypes from "prop-types";
import Backend from "../../Backend.js";

class Header extends Component {
    constructor() {
        super();
    }

    onLogout() {
        Backend.logout();
        this.props.onLogout();
    }

    render() {
        return (
            <Fragment>
                <div className={"header"}>
                    <div className={"logo"}>Picsit</div>
                    <div className={"score"}>{isNaN(this.props.score) ? '' : 'Deine Punktzahl: ' + this.props.score}</div>
                    <i className={"fa fa-power-off clickable"} onClick={this.onLogout.bind(this)}/>
                </div>
                <div className={"notification"}>{this.props.notification}</div>
            </Fragment>
        );
    }
}

Header.propTypes = {
    notification: PropTypes.string.isRequired,
    score: PropTypes.number,
    onLogout: PropTypes.func.isRequired
};

export default Header;