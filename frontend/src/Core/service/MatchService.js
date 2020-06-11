export default class MatchService {

    constructor(httpRequest) {
        this.httpRequest = httpRequest;
    }

    getAllMatches() {
        return this.httpRequest.get('matches').then(data => data);
    }

    getMatchById(matchId) {
        return this.httpRequest.get(`matches/${matchId}`).then(data => data);
    }

}