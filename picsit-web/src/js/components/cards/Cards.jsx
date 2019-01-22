import React, {Component, Fragment} from "react";
import "./Cards.scss";
import PropTypes from "prop-types";
import Backend from "../../Backend";

class Cards extends Component {

    constructor() {
        super();
        this.state = {
            textEntered: false,
            detail: false,
            selectedIndex: -1
        };
        this.text = '';
    }

    onChangeHandler(evt) {
        this.text = evt.target.value;
        if (this.text.trim()) {
            this.setState({textEntered: true})
        } else {
            this.setState({textEntered: false})
        }
    }

    next() {
        this.setState({selectedIndex: (this.state.selectedIndex + 1) % this.props.cardsOnHand.length});
    }

    previous() {
        this.setState({selectedIndex: (this.state.selectedIndex - 1 + this.props.cardsOnHand.length) % this.props.cardsOnHand.length});
    }

    chooseCardAndTitle() {
        const cardId = this.props.cardsOnHand[this.state.selectedIndex];
        Backend.choosePicWithTitle(cardId, this.text.trim()).then(() => {
            this.props.onChange()
        });
        this.state.detail = false;
    }

    chooseCard() {
        const cardId = this.props.cardsOnHand[this.state.selectedIndex];
        Backend.choosePic(cardId).then(() => {
            this.props.onChange()
        });
        this.state.detail = false;
    }

    renderDefault() {
        return this.props.cardsOnHand.map((cardId, index) => {
            const className = 'c-cards__card' + (index === this.state.selectedIndex && !this.props.waiting ? ' selected' : '');
            return <div key={index}
                        className={className}
                        onClick={() => this.setState({detail: true, selectedIndex: index})}>
                <img alt={cardId} src={'cards/' + cardId}/>
            </div>
        });
    }

    renderDetail() {
        const cardId = this.props.cardsOnHand[this.state.selectedIndex];
        return <div className={'cardDetail'}>
            <div className={'previous clickable'} onClick={() => this.previous()}/>
            <div key={cardId} className={'c-cards__card -max selected'} onClick={() => this.setState({detail: false})}>
                <img alt={cardId} src={'cards/' + cardId}/>
            </div>
            <div className={'next clickable'} onClick={() => this.next()}/>
        </div>;
    }

    render() {
        const cards = this.state.detail ? this.renderDetail() : this.renderDefault();
        const detail = this.state.detail;
        let cardsContainer = 'margin-bottom-20 stretch c-cards__container';
        const btnCheckCardAndTitle = 'btn btn-check' + (!this.state.textEntered || this.state.selectedIndex === -1 ? ' disabled' : '');
        const btnCheckCard = 'btn btn-check' + (this.state.selectedIndex === -1 ? ' disabled' : '');
        return <div className={'c-cards'}>
            {this.props.title ? <div className={'title margin-bottom-20'}>Titel: {this.props.title}</div> : ''}
            <div className={cardsContainer}>{cards}</div>
            {this.props.yourTurn && !this.props.title ?
                <Fragment>
                    <textarea className={'margin-bottom-20'} onChange={evt => this.onChangeHandler(evt)}/>
                    <div className={btnCheckCardAndTitle} onClick={() => this.chooseCardAndTitle()}>Wähle Karte und
                        Titel
                    </div>
                </Fragment> : ''}
            {!this.props.yourTurn && this.props.title && !this.props.waiting ?
                <Fragment>
                    <div className={btnCheckCard} onClick={() => this.chooseCard()}>Wähle Karte</div>
                </Fragment> : ''}
        </div>;
    }
}

Cards.propTypes = {
    cardsOnHand: PropTypes.arrayOf(PropTypes.number).isRequired,
    onChange: PropTypes.func.isRequired,
    yourTurn: PropTypes.bool.isRequired,
    title: PropTypes.string.isRequired,
    waiting: PropTypes.bool.isRequired
};

export default Cards;