<template>
    <div class="users-ranking">
        <b-container>
            <b-row>
                <!--Players-->
                <b-col cols="9">
                    <div class="users">
                        <ul>
                            <li v-for="(user, index) in users"
                                v-bind:key="user.id"
                                v-bind:not-last="isNotLast(index)"
                                v-bind:first="isFirst(index)"
                                v-on:click="showPlayer(user.id)">
                                <b-row>
                                    <b-col>
                                        <b-avatar :src="user.icon"></b-avatar>
                                    </b-col>
                                    <b-col>
                                        <a>{{user.nick}}</a>
                                    </b-col>
                                    <b-col>
                                        <a>{{user.level}} level</a>
                                    </b-col>
                                </b-row>
                            </li>
                        </ul>
                    </div>
                </b-col>

                <!--Filter-->
                <b-col cols="3">

                </b-col>
            </b-row>
        </b-container>
    </div>
</template>

<script>
    export default {
        name: "UsersRanking",
        data: function () {
            return {
                users: [{
                    id: 1,
                    nick: 'Arturito',
                    level: 2,
                    icon: require('../assets/profileIcon.png')
                },
                    {
                        id: 2,
                        nick: 'Michacy',
                        level: 3,
                        icon: require('../assets/profileIcon.png')
                    },
                    {
                        id: 3,
                        nick: 'Maciek12',
                        level: 1,
                        icon: require('../assets/appLogo.png')
                    }]
            }
        },
        methods: {
            isNotLast: function (index) {
                return !(index + 1 === this.users.length) ? 1 : 0;
            },
            isFirst: function (index) {
                return index === 0 ? 1 : 0;
            },
            showPlayer: function (id) {
                this.$router.push(`/user/${id}`);
            }
        }
    }
</script>

<style scoped>
    .users {
        background: rgba(0, 0, 0, 0.4);
        border-radius: 15px;
        margin-top: 60px;
    }

    ul {
        list-style: none;
        padding: 0;
        border-radius: 15px;
    }

    li {
        padding-top: 25px;
        padding-bottom: 25px;
    }

    li:hover {
        cursor: pointer;
        background: rgba(255, 255, 255, .1);
    }

    li[not-last="1"] {
        border-bottom: 1px solid #cfcfcf;
    }

    li[not-last="0"] {
        border-bottom-left-radius: 15px;
        border-bottom-right-radius: 15px;
    }

    li[first="1"] {
        border-top-left-radius: 15px;
        border-top-right-radius: 15px;
    }

    a {
        color: #cfcfcf !important;
    }

    .b-avatar {
        border-radius: 50%;
        border: 2px solid;
        color: var(--colour3);
        margin-top: 5px;
        background: none;
    }
</style>
