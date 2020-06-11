export default class AuthorizationStorage {

    getAuthorization() {
        if (this.isAuthorized()) {
            return {
                token: (sessionStorage.getItem('authorizationToken')),
                user: JSON.parse(sessionStorage.getItem('user'))
            };
        }
        return null;
    }

    setAuthorization(token, user) {
        sessionStorage.setItem('authorizationToken', token);
        sessionStorage.setItem('user', JSON.stringify(user));
    }

    isAuthorized() {
        return sessionStorage.getItem('authorizationToken') !== null;
    }

    clear() {
        sessionStorage.clear();
    }
}
