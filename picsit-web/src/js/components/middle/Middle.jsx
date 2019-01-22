import React, {Component, Fragment} from "react";
import "./Middle.scss";
import PropTypes from "prop-types";
import Backend from "../../Backend";
import Cookie from "../../Cookie";

class Middle extends Component {

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
        this.setState({selectedIndex: (this.state.selectedIndex + 1) % this.props.cardsInMiddle.length});
    }

    previous() {
        this.setState({selectedIndex: (this.state.selectedIndex - 1 + this.props.cardsInMiddle.length) % this.props.cardsInMiddle.length});
    }

    pickCard() {
        const cardId = this.props.cardsInMiddle[this.state.selectedIndex].cardIndex;
        Backend.pickPic(cardId).then(() => {
            this.props.onChange();
        });
        this.state.detail = false;
    }

    nextRound() {
        Cookie.clear('TEXTING-COOKIE-LATEST-TITLE');
        this.props.onChange();
    }

    renderDefault() {
        return this.props.cardsInMiddle.map((card, index) => {
            const className = 'c-cards__card'
                + (index === this.state.selectedIndex && !this.props.waiting ? ' selected' : '')
                + (this.props.overview ? (card.correct ? ' correct' : ' incorrect') : '')
                + (card.cardIndex === this.props.myCard ? ' own' : '')
                + (card.cardIndex === this.props.myPickedCard ? ' picked' : '');
            return <div key={index}
                        className={className}
                        onClick={() => this.setState({detail: true, selectedIndex: index})}>
                <img alt={card.cardIndex} src={'cards/' + card.cardIndex}/>
                {this.props.overview ? <span className={'count'}>{card.playersThatPickedIt.length}</span> : ''}
            </div>
        });
    }

    renderDetail() {
        const card = this.props.cardsInMiddle[this.state.selectedIndex];
        const cardId = card.cardIndex;
        const className = 'c-cards__card -max selected'
            + (this.props.overview ? (card.correct ? ' correct' : ' incorrect') : '')
            + (cardId === this.props.myCard ? ' own' : '')
            + (cardId === this.props.myPickedCard ? ' picked' : '');
        return <Fragment>
            <div className={'cardDetail'}>
                <div className={'previous clickable'} onClick={() => this.previous()}/>
                <div key={cardId} className={className} onClick={() => this.setState({detail: false})}>
                    <img alt={cardId} src={'cards/' + cardId}/>
                </div>
                <div className={'next clickable'} onClick={() => this.next()}/>
            </div>
            {this.props.overview ?
                <div className={'details'}>
                    <div className={'owner margin-bottom-20'}>gelegt
                        von {this.props.players[card.playerThatPutItDown]}</div>
                    {card.playersThatPickedIt.length > 0 ? <Fragment>
                        <div className={'margin-bottom-20'}>Gewählt von</div>
                        <ul className={'list'}>{card.playersThatPickedIt.map(playerIndex =>
                            <li>{this.props.players[playerIndex]}</li>)}</ul>
                    </Fragment> : ''}
                </div>
                : ''}
        </Fragment>;
    }

    render() {
        const cards = this.state.detail ? this.renderDetail() : this.renderDefault();
        const overview = this.props.overview;
        let cardsContainer = 'margin-bottom-20 stretch c-cards__container';
        const btnCheckCard = 'btn btn-check' + (this.state.selectedIndex === -1 ? ' disabled' : '');
        return <div className={'c-cards'}>
            {this.props.overview ?
                <div className={'margin-bottom-20'}>Erhaltene Punkte: {this.props.myPoints}</div> : ''}
            {this.props.title ? <div className={'title margin-bottom-20'}>Titel: {this.props.title}</div> : ''}
            <div className={cardsContainer}>{cards}</div>
            {!this.props.waiting ?
                <Fragment>
                    <div className={btnCheckCard} onClick={() => this.pickCard()}>Wähle Karte</div>
                </Fragment> : ''}
            {overview ?
                <Fragment>
                    <div className={'btn btn-start'} onClick={() => this.nextRound()}>Starte
                        nächste Runde
                    </div>
                </Fragment> : ''}
        </div>;
    }
}

Middle.propTypes = {
    cardsInMiddle: PropTypes.arrayOf(PropTypes.any).isRequired,
    onChange: PropTypes.func.isRequired,
    waiting: PropTypes.bool.isRequired,
    title: PropTypes.string.isRequired,
    myCard: PropTypes.number.isRequired,
    myPickedCard: PropTypes.number.isRequired,
    players: PropTypes.arrayOf(PropTypes.string).isRequired,
    myPoints: PropTypes.number.isRequired,
    overview: PropTypes.bool.isRequired
};

export default Middle;