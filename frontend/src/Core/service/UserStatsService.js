export default class UserStatsService {

    constructor(httpRequest) {
        this.httpRequest = httpRequest;
    }

    getStats() {
        return this.httpRequest.get('users/me').then(data => data);
    }
}