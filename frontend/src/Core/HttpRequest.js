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

    patch(url, data) {
        return this.execute(url, 'PATCH', data);
    }

    execute(url, method, data) {
        let headers = new Headers({
            'Content-Type': 'application/json-patch+json', 'Access-Control-Allow-Origin': '*',
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
                    return response.json().then(error => {
                        return {errors: error};
                    })
                }
                return response.json();
            })
    }
}