export default class BetService {

    constructor(httpRequest) {
        this.httpRequest = httpRequest;
    }

    getBetsForUser(userId) {
        return this.httpRequest.get(`bets/user/${userId}`).then(data => data);
    }

    createBet(data){
        return this.httpRequest.post('bets', data).then(data => data);
    }
}