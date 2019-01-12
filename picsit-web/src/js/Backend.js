import axios from "axios";

export default class Backend {
    static login(name) {
        return axios.get('/login', {params: {name}});
    }

    static logout() {
        return axios.get('/logout', {params: {name}, withCredentials: true});
    }

    static newGame() {
        return axios.get('/game/newP',
            {params: {nofCardsInPile: 84, nofCardsOnHand: 6, typeTitle: true}, withCredentials: true});
    }

    static startGame() {
        return axios.get('/game/start', {withCredentials: true});
    }

    static pollGameStatus() {
        return axios.get('/game/status/poll', {withCredentials: true});
    }

    static pollAvailableGames() {
        return axios.get('/games/unstarted/poll', {withCredentials: true});
    }

    static enterGame() {
        return axios.get('/game/enter', {withCredentials: true});
    }

    static choosePicWithTitle(card, title) {
        return axios.get('/game/putPic/title', {params: {card, title}, withCredentials: true});
    }

    static choosePic(card) {
        return axios.get('/game/putPic/down', {params: {card}, withCredentials: true});
    }

    static pickPic(card) {
        return axios.get('/game/pick/Pic', {params: {card}, withCredentials: true});
    }

    static leaveGame() {
        return axios.get('/game/leave', {withCredentials: true});
    }
}