export default class AuthorizationService {
    constructor(httpRequest) {
        this.httpRequest = httpRequest;
    }

    login(username, password) {
        return this.httpRequest.post("auth/login", {username: username, password: password}).then(data => data);
    }

    register(username, password, email, firstName, lastName) {
        return this.httpRequest.post("users", {
            username: username,
            password: password,
            email: email,
            firstName: firstName,
            lastName: lastName
        }).then(data => data);
    }
}