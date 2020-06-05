export default class BetService {

    constructor(httpRequest) {
        this.httpRequest = httpRequest;
    }

    getBetsForUser(userId) {
        return this.httpRequest.get(`bets/user/${userId}`).then(data => data);
    }
}