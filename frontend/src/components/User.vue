<template>
    <div class="user">
        <loading v-if="loading"></loading>
        <b-container v-else>
            <b-row align-h="center">
                <b-col cols=8>
                    <div class="profile-panel">
                        <div>
                            <div class="profile-icon">
                                <b-img :src="profile.icon" height="250px" width="250px"></b-img>
                            </div>
                            <div class="lvl">
                                {{profile.lvl}} lvl
                            </div>
                            <b-progress v-b-tooltip.hover.bottom="'Exp: ' + profile.exp + '/' + profile.expToNextLvl">
                                <b-progress-bar :value="profile.exp" :max="profile.expToNextLvl"></b-progress-bar>
                            </b-progress>
                        </div>

                        <div class="nick-name">
                            <p>{{profile.nick}}</p>
                            <p class="details">Ranking: {{profile.ranking}}</p>
                        </div>
                    </div>
                </b-col>
            </b-row>
        </b-container>
    </div>
</template>

<script>
    import {publicUsersService} from "../App";
    import {usersRankingService} from "../App";
    import LevelToIconMapper from "../Core/LevelToIconMapper";
    import Loading from "./Loading";

    export default {
        name: "User",
        components: {Loading},
        data: function () {
            return {
                profile:
                    {
                        id: null,
                        nick: 'Arturito',
                        lvl: 12,
                        exp: 5,
                        expToNextLvl: 20,
                        ranking: 0,
                        icon: null,
                    },
                loading: true
            }
        },
        mounted(){
            this.profile.id = this.$route.params.id
            publicUsersService.getUser(this.profile.id).then(
                response => {
                    if(response.errors){
                        this.$bvToast.toast(response.errors.message, {
                            title: 'Error',
                            variant: 'danger',
                            toaster: 'b-toaster-top-center'
                        });
                    } else {
                        this.profile.nick = response.username;
                        this.profile.lvl = response.level.id;
                        this.profile.exp = response.exp;
                        this.profile.expToNextLvl = response.level.expToNextLevel;
                        this.profile.icon = LevelToIconMapper.getUrl(response.level.id);
                        usersRankingService.getUserRanking(this.profile.nick).then(
                            response => {
                                if (response.errors) {
                                    this.$bvToast.toast(response.errors.message, {
                                        title: 'Error',
                                        variant: 'danger',
                                        toaster: 'b-toaster-top-center'
                                    });
                                } else {
                                    this.profile.ranking = response.place;
                                }
                                this.loading = false;
                            }
                        )
                    }
                }
            )
        }
    }
</script>

<style scoped>
    .profile-panel {
        margin-top: 60px;
        padding-top: 25px;
        padding-bottom: 25px;
        padding-left: 25px;
        text-align: left;
        display: flex;
        background: rgba(0, 0, 0, 0.4);
        border-radius: 15px;
    }

    .profile-icon {
        background: linear-gradient(to bottom left, var(--colour5) 20%, var(--colour8));
        border: none;
        position: relative;
        height: 250px;
        width: 250px;
        z-index: 10;
        border-radius: 30%;
    }

    div.profile-icon:after {
        content: "";
        position: absolute;
        padding: 10px;
        background: #1F2833;
        top: 5px;
        bottom: 5px;
        left: 5px;
        right: 5px;
        border-radius: 30%;
        z-index: -1;
    }

    .nick-name {
        color: var(--colour5);
        margin-left: 40px;
        margin-top: 10px;
        font-size: 40px;
    }

    .name {
        font-size: 25px !important;
        color: var(--colour4) !important;
    }

    .lvl {
        text-align: center;
        color: #b600b9;
        font-size: 30px;
    }

    .details {
        font-size: 25px !important;
        color: var(--colour4) !important;
    }

    .progress {
        height: 15px;
        background-color: #d6d8db !important;
        margin-right: 0;
        min-width: 80px;
    }

    .progress .progress-bar {
        background: #b600b9 !important;
    }

    img {
        border-radius: 30%;
        padding: 5px;
    }
</style>
