<template>
    <div class="profile">
        <b-container>
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
                            <p class="name">{{profile.firstName}} {{profile.lastName}}</p>
                            <p class="name">{{profile.email}}</p>
                            <p class="name">Ranking: {{ranking}}</p>
                        </div>
                    </div>
                </b-col>
            </b-row>
            <b-row align-h="center">
                <b-col cols=10>
                    <match-history></match-history>
                </b-col>
            </b-row>
        </b-container>
    </div>
</template>

<script>

    import MatchHistory from "./MatchHistory";
    import {usersRankingService} from "../App";

    export default {
        name: "Profile",
        components: {MatchHistory},
        data: function(){
            return{
                ranking: null
            }
        },
        computed: {
            profile: function () {
                return {
                    nick: this.$store.state.user.username,
                    firstName: this.$store.state.user.firstName,
                    lastName: this.$store.state.user.lastName,
                    email: this.$store.state.user.email,
                    lvl: this.$store.state.user.level.id,
                    exp: this.$store.state.user.exp,
                    expToNextLvl: this.$store.state.user.level.expToNextLevel,
                    tokens: this.$store.state.user.coins,
                    icon: this.$store.state.user.icon,
                }
            }
        },
            mounted(){
                usersRankingService.getMyRanking().then(response => {
                    this.ranking = response.place
                })
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
