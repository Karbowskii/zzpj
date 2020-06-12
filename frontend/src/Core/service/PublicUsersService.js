
export default class PublicUsersService{
    constructor(httpRequest) {
        this.httpRequest = httpRequest;
    }

    getUser(id){
        return this.httpRequest.get(`users/${id}`).then(data => data);
    }
}