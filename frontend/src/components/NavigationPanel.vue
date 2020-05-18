<template>
    <div id="navigation-panel">
        <b-navbar>
            <b-navbar-brand>
                <b-link :to="{path:'/'}">
                    <b-img :src="require('../assets/appLogo.png')" width="43" height="54"></b-img>
                </b-link>
            </b-navbar-brand>
            <b-navbar-nav class="navigationRouter">
                <b-nav-item>
                    <b-link :to="{path:'/schedule'}">Schedule</b-link>
                </b-nav-item>
                <b-nav-item>
                    <b-link :to="{path:'/users-ranking'}">Ranking</b-link>
                </b-nav-item>
            </b-navbar-nav>
            <b-navbar-nav class="ml-auto" v-if="isLogged===false">
                <b-nav-item right>
                    <b-button v-on:click="logIn">Sign in</b-button>
                </b-nav-item>
            </b-navbar-nav>


            <b-navbar-nav class="ml-auto tokens" v-if="isLogged===true">
                <a>{{user.tokensNumber}}</a>
            </b-navbar-nav>
            <b-navbar-nav class="ml-auto account" v-if="isLogged===true" right>
                <b-nav-item>
                    <b-link :to="{path:'/Profile'}" class="profile">
                        <b-img :src="require('../assets/profileIcon.png')" width="50" height="50"></b-img>
                    </b-link>
                </b-nav-item>
                <a class="lvl">{{user.lvl}}</a>
                <b-nav-item>
                    <b-link :to="{path:'/Profile'}" class="profile">
                        <a>{{user.nickname}}</a>
                        <b-progress>
                            <b-progress-bar :max="100" :value="user.expPrc"></b-progress-bar>
                        </b-progress>
                    </b-link>
                </b-nav-item>
            </b-navbar-nav>
            <b-navbar-nav class="ml-auto logout" v-if="isLogged===true">
                <b-nav-item right>
                    <b-button v-on:click="logOut">Log out</b-button>
                </b-nav-item>
            </b-navbar-nav>
        </b-navbar>
    </div>
</template>

<script>
    export default {
        name: "NavigationPanel",
        methods: {
            logIn: function () {
                if (this.$route.path !== '/login') {
                    this.$router.push({path: '/login'})
                }
            },
            logOut: function () {
                this.$store.commit('logout');
                if (this.$route.path !== '/') {
                    this.$router.push({path: '/'})
                }
            }
        },
        computed: {
            isLogged() {
                return this.$store.getters.isAuthorized;
            },
            user() {
                return {nickname: this.$store.state.user, tokensNumber: 21, lvl: 12, expPrc: 20};
            }
        },
    }
</script>

<style scoped>
    #navigation-panel {
        position: sticky;
        top: 0;
        z-index: 100;
    }

    nav {
        background: var(--colour2);
        box-shadow: 0 3px 13px 5px var(--colour4);
    }

    a.nav-link a {
        color: var(--colour4);
        font-size: 24px;
        padding-bottom: 8px;
    }

    .navigationRouter li.nav-item {
        margin-right: 15px;
        margin-left: 15px;
    }

    .navigationRouter li.nav-item a.nav-link a:hover {
        color: var(--colour5);
        text-decoration: none;
    }

    img {
        margin-left: 15px;
    }

    .profile a {
        color: var(--colour4) !important;
    }

    .profile {
        display: block;
    }

    a.profile {
        text-decoration: none !important;
    }

    .profile img {
        margin-right: 15px;
        border-radius: 50%;
        border: 2px solid;
        color: var(--colour3);
        margin-top: 5px;
    }

    .logout {
        margin-left: 0 !important;
    }

    .account {
        margin-left: 0 !important;
    }

    button {
        border: 2px solid var(--colour4);
        background: none;
        color: #b9b9b9;
    }

    button:hover {
        border: 2px solid var(--colour4);
        background: none;
        text-shadow: 0 0 5px var(--colour5);
    }

    .navigationRouter .router-link-exact-active {
        border-bottom: 3px solid var(--colour3);
        color: var(--colour3);
        font-weight: bold;
    }

    .navigationRouter .router-link-exact-active:hover {
        border-bottom: 3px solid var(--colour5) !important;
    }

    .progress {
        height: 10px;
        background-color: #d6d8db !important;
        margin-right: 0;
        min-width: 80px;
    }

    .progress .progress-bar {
        background: var(--colour3) !important;
    }

    a.lvl {
        color: var(--colour4);
        font-size: 25px;
        margin-top: 30px;
    }

    a.lvl:hover {
        color: var(--colour4) !important;
    }

    .tokens a {
        color: var(--colour4);
        vertical-align: top;
        font-size: 40px;
        margin-top: 5px;
    }

    .tokens a:hover {
        color: var(--colour4);
    }

</style>
