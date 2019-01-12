import React, {Component, Fragment} from "react";
import "./Middle.scss";
import PropTypes from "prop-types";
import Backend from "../../Backend";

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
        const cardId = this.props.cardsInMiddle[this.state.selectedIndex];
        Backend.pickPic(cardId).then(() => {
            this.props.onChange()
        });
        this.state.detail = false;
    }

    renderDefault() {
        return this.props.cardsInMiddle.map((cardId, index) => {
            const className = 'c-cards__card'
                + (index === this.state.selectedIndex && !this.props.waiting ? ' selected' : '');
            return <div key={index}
                        className={className}
                        onClick={() => this.setState({detail: true, selectedIndex: index})}>
                <img alt={cardId} src={'cards/' + cardId}/>
            </div>
        });
    }

    renderDetail() {
        const cardId = this.props.cardsInMiddle[this.state.selectedIndex];
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
        const btnCheckCard = 'btn btn-check' + (this.state.selectedIndex === -1 ? ' disabled' : '');
        return <div className={'c-cards'}>
            {this.props.title ? <div className={'margin-bottom-20'}>{this.props.title}</div> : ''}
            <div className={cardsContainer}>{cards}</div>
            {!this.props.waiting ?
                <Fragment>
                    <div className={btnCheckCard} onClick={() => this.pickCard()}>Wähle Karte</div>
                </Fragment> : ''}
        </div>;
    }
}

Middle.propTypes = {
    cardsInMiddle: PropTypes.arrayOf(PropTypes.number).isRequired,
    onChange: PropTypes.func.isRequired,
    waiting: PropTypes.bool.isRequired,
    title: PropTypes.string.isRequired,
    myCard: PropTypes.number.isRequired
};

export default Middle;