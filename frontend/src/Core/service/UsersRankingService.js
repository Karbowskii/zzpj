export default class UsersRankingService {

    constructor(httpRequest) {
        this.httpRequest = httpRequest;
    }

    getRanking() {
        return this.httpRequest.get(`ranking`).then(data => data);
    }

    getMyRanking(){
        return this.httpRequest.get(`ranking/me`).then(data => data);
    }

    getUserRanking(username){
        return this.httpRequest.get(`ranking/${username}`).then(data => data);
    }
}