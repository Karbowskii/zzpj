<template>
    <div id="app">
        <NavigationPanel></NavigationPanel>
        <router-view/>
    </div>
</template>

<script>
    import NavigationPanel from "./components/NavigationPanel";
    import AuthorizationStorage from "./Core/AuthorizationStorage";

    export const authorizationStorage = new AuthorizationStorage();


    export default {
        name: 'App',
        components: {NavigationPanel},
        mounted() {
            if (authorizationStorage.isAuthorized()) {
                this.$store.commit('login', authorizationStorage.getAuthorization())
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
