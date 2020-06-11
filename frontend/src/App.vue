<template>
    <div id="app">
        <NavigationPanel></NavigationPanel>
        <router-view/>
    </div>
</template>

<script>
    import NavigationPanel from "./components/NavigationPanel";
    import AuthorizationStorage from "./Core/AuthorizationStorage";
    import AuthorizationService from "./Core/service/AuthorizationService";
    import HttpRequest from "./Core/HttpRequest";
    import BetService from "./Core/service/BetService";
    import UsersRankingService from "./Core/service/UsersRankingService";

    export const authorizationStorage = new AuthorizationStorage();
    export const httpRequest = new HttpRequest(process.env.VUE_APP_URL, authorizationStorage);
    export const authorizationService = new AuthorizationService(httpRequest);
    export const betService = new BetService(httpRequest);
    export const usersRankingService = new UsersRankingService(httpRequest);


    export default {
        name: 'App',
        components: {NavigationPanel},
        created() {
            if (authorizationStorage.isAuthorized()) {
                this.$store.state.user = authorizationStorage.getAuthorization().user;
                this.$store.state.token = authorizationStorage.getAuthorization().token;
            }
        }
    }
</script>

<style>
    #app {
        font-family: Avenir, Helvetica, Arial, sans-serif;
        text-align: center;
        height: 100%;
    }

    html, body {
        height: 100%;
        background: url("assets/89503.jpg") no-repeat center center fixed;
        background-size: cover;
    }
</style>
