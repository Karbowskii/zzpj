export default class UserService {

    constructor(httpRequest) {
        this.httpRequest = httpRequest;
    }

    updateUserAccount(data) {
        return this.httpRequest.patch('users/me', data).then(data => data);
    }
}