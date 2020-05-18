export default class AuthorizationStorage {

    getAuthorization() {
        if (this.isAuthorized()) {
            return {
                token: (sessionStorage.getItem('authorizationToken')),
                user: (sessionStorage.getItem('user'))
            };
        }
        return null;
    }

    setAuthorization(token, user) {
        sessionStorage.setItem('authorizationToken', token);
        sessionStorage.setItem('user', user);
    }

    isAuthorized() {
        return sessionStorage.getItem('authorizationToken') !== null;
    }

    clear() {
        sessionStorage.clear();
    }
}
