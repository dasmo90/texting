import React, {Component, Fragment} from "react";
import "./Cards.scss";
import PropTypes from "prop-types";
import Backend from "../../Backend";

class Cards extends Component {

    constructor() {
        super();
        this.state = {
            detail: false,
            selectedIndex: -1
        }

    }

    chooseCard(card, title) {
        Backend.choosePicWithTitle(card, title).then(() => {
            this.props.onChange()
        });
    }

    render() {
        return this.state.detail ? this.renderDetail() : this.renderDefault();
    }

    renderDefault() {
        const cards = this.props.cardsOnHand.map((cardId, index) =>
            <div key={index}
                 className={'c-cards__card'}
                 onClick={() => this.setState({detail: true, selectedIndex: index})}>
                <img alt={cardId} src={'cards/' + cardId}/>
            </div>
        );
        return <div className={'c-cards__cards stretch'}>{cards}</div>;
    }

    renderDetail() {
        const cards = this.props.cardsOnHand.map((cardId, index) => {
                let extraClass = '';
                if (index < this.state.selectedIndex) {
                    extraClass = ' -left';
                } else if (index > this.state.selectedIndex) {
                    extraClass = ' -right';
                }
                return <Fragment>
                    <span className={'previous'}></span>
                    <div key={cardId} className={'c-cards__card -max' + extraClass}>
                        <img alt={cardId} src={'cards/' + cardId}/>
                    </div>
                    <span className={'next'}></span>
                </Fragment>;
            }
        );
        return <div className={'c-cards__cards -max stretch'}>{cards}</div>;
    }
}

Cards.propTypes = {
    cardsOnHand: PropTypes.arrayOf(PropTypes.number).isRequired,
    onChange: PropTypes.func.isRequired,
    yourTurn: PropTypes.bool.isRequired
};

export default Cards;