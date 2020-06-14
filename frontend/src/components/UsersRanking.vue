<template>
    <div class="users-ranking">
        <b-container>
            <b-row>
                <!--Players-->
                <b-col cols="9">
                    <div class="users">
                        <ul>
                            <li v-for="(user, place) in users"
                                v-bind:key="user.id"
                                v-bind:not-last="isNotLast(place)"
                                v-bind:first="isFirst(place)"
                                v-on:click="showPlayer(user.user.id)">
                                <b-row>
                                    <b-col cols="2">
                                        <a class="place">#{{user.place}}</a>
                                    </b-col>
                                    <b-col cols="2">
                                        <b-avatar :src="getIcon(user.user.level.id)"></b-avatar>
                                    </b-col>
                                    <b-col>
                                        <a class="details">{{user.user.username}}</a>
                                    </b-col>
                                    <b-col>
                                        <a class="details">{{user.user.level.id}} level</a>
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
    import {usersRankingService} from "../App";
    import LevelToIconMapper from "../Core/LevelToIconMapper";
    export default {
        name: "UsersRanking",
        data: function () {
            return {
                users: [],
            }
        },
        mounted() {
            usersRankingService.getRanking().then(response => {
                this.users = response
            })
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
            },
            getIcon: function(level){
                return LevelToIconMapper.getUrl(level)
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

    .details {
        color: #cfcfcf !important;
        padding-top: 20px!important;
    }

    .place{
        color: var(--colour5);
        padding-top: 13px;
        font-size: 25px;
    }

    .b-avatar {
        border-radius: 50%;
        border: 2px solid;
        color: var(--colour3);
        margin-top: 5px;
        background: none!important;
    }
</style>
