export default class HttpRequest {

    constructor(baseUrl, authorizationStorage) {
        this.baseUrl = baseUrl;
        this.authorizationStorage = authorizationStorage;
    }

    get(url) {
        return this.execute(url, 'GET');
    }

    post(url, data) {
        return this.execute(url, 'POST', data);
    }

    put(url, data) {
        return this.execute(url, 'PUT', data);
    }

    delete(url) {
        return this.execute(url, 'DELETE');
    }

    execute(url, method, data) {
        let headers = new Headers({
            'Content-Type': 'application/json', 'Access-Control-Allow-Origin': '*',
            'Access-Control-Request-Headers': '*'
        });
        if (this.authorizationStorage.isAuthorized()) {
            headers.append('Authorization', 'Bearer ' + this.authorizationStorage.getAuthorization()?.token);
        }
        return fetch(`${this.baseUrl}/${url}`, {
            method: method,
            headers: headers,
            cache: 'no-cache',
            body: JSON.stringify(data)
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error("HTTP status " + response.status);
                }
                return response.json();
            })
            .catch(err => {
                return {errors: [err]}
            })
    }
}