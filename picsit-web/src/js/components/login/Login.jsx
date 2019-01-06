import React, {Component, Fragment} from "react";
import "./Login.scss";
import Backend from "../../Backend.js";
import PropTypes from "prop-types";

class Login extends Component {

    constructor() {
        super();
        this.name = '';
        this.state = {
            disabled: true
        }
    }

    login() {
        if (!this.state.disabled) {
            Backend.login(this.name).then(this.props.onLogin);
        }
    }

    onChangeHandler(evt) {
        this.name = evt.target.value;
        if (this.name) {
            this.setState({disabled: false})
        } else {
            this.setState({disabled: true})
        }
    }

    render() {
        const btnNextClassName = "btn btn-next" + (this.state.disabled ? " disabled" : "");
        return (
            <Fragment>
                <div className={"margin-bottom-40"}>Willkommen zu Picsit</div>
                <div className={"enterName margin-bottom-40"}>
                    <label>Name</label>
                    <input onChange={evt => this.onChangeHandler(evt)}/></div>
                <div className={btnNextClassName} onClick={this.login.bind(this)}>Weiter</div>
            </Fragment>
        );
    }
}

Login.propTypes = {
    onLogin: PropTypes.func.isRequired
};

export default Login;