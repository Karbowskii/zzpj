<template>
    <div class="profile">
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

                        <div class="nick-name" v-if="!isEditing">
                            <p>{{profile.nick}}</p>
                            <p class="name">Ranking: {{ranking}}</p>
                            <p class="name">{{profile.firstName}} {{profile.lastName}}</p>
                            <p class="name">{{profile.email}}</p>
                        </div>
                        <div class="edit" v-if="!isEditing">
                            <b-button v-on:click="enterEditing">Edit</b-button>
                        </div>
                        <b-form @submit.prevent="editAccount" id="edit-form" v-else>
                            <div class="nick-name">
                                <p>{{profile.nick}}</p>
                                <p class="name">Ranking: {{ranking}}</p>

                                <b-form-input required type="text" v-model="editProfile.firstName"></b-form-input>
                                <b-form-input required type="text" v-model="editProfile.lastName"></b-form-input>
                                <b-form-input required type="email" v-model="editProfile.email"></b-form-input>
                                <b-button type="submit">Save</b-button>
                                <b-button class="ml-2" v-on:click="leaveEditing">Cancel</b-button>
                            </div>
                        </b-form>
                    </div>
                </b-col>
            </b-row>
            <b-row class="stats-section" align-h="center" v-if="anyData">
                <b-col cols="5" class="left">
                    <user-statistics :labels="['Wins', 'Loses']" :series="betStats"></user-statistics>
                </b-col>
                <b-col cols="5" class="right">
                    <user-statistics :labels="['Gain PepeCoins','Lost PepeCoins']"
                                     :series="coinsStats"></user-statistics>
                </b-col>
            </b-row>

            <b-row align-h="center">
                <b-col cols=12>
                    <match-history></match-history>
                </b-col>
            </b-row>
        </b-container>
    </div>
</template>

<script>

    import MatchHistory from "./MatchHistory";
    import UserStatistics from "./UserStatistics";
    import {usersRankingService} from "../App";
    import {userStatsService} from "../App";
    import {userService} from "../App";
    import LevelToIconMapper from "../Core/LevelToIconMapper";
    import Loading from "./Loading";

    export default {
        name: "Profile",
        components: {Loading, UserStatistics, MatchHistory},
        data: function () {
            return {
                ranking: null,
                stats: {},
                loading: true,
                isEditing: false,
                editProfile: {
                    firstName: "",
                    lastName: "",
                    email: ""
                }
            }
        },
        methods: {
            enterEditing() {
                this.editProfile.firstName = this.$store.state.user.firstName;
                this.editProfile.lastName = this.$store.state.user.lastName;
                this.editProfile.email = this.$store.state.user.email;
                this.isEditing = true;
            },
            leaveEditing() {
                this.isEditing = false;
            },
            editAccount() {
                userService.updateUserAccount([{
                    op: 'replace',
                    path: '/firstName',
                    value: this.editProfile.firstName
                }, {
                    op: 'replace',
                    path: '/lastName',
                    value: this.editProfile.lastName
                }, {
                    op: 'replace',
                    path: '/email',
                    value: this.editProfile.email
                }]).then((response) => {
                    if (response.errors) {
                        this.$bvToast.toast(response.errors.message, {
                            title: 'Error',
                            variant: 'danger',
                            toaster: 'b-toaster-top-center'
                        });
                    } else {
                        this.$bvToast.toast('Your account details have been changed!', {
                            title: 'Account update',
                            variant: 'success',
                            toaster: 'b-toaster-top-center'
                        });
                        response.icon = LevelToIconMapper.getUrl(response.level.id);
                        this.$store.commit('updateUser', {user: response});
                    }
                    this.leaveEditing();

                })
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
                    icon: this.$store.state.user.icon
                }
            },
            betStats: function () {
                let result = [];
                result.push(this.stats.goodBets);
                result.push(this.stats.badBets);
                return result;
            },
            coinsStats: function () {
                let result = [];
                result.push(this.stats.earnedCoins);
                result.push(this.stats.lostCoins);
                return result;
            },
            anyData: function () {
                return this.stats.allBets > 0 && (this.stats.earnedCoins + this.stats.lostCoins) > 0
            }
        },
        created() {
            Promise.all([usersRankingService.getMyRanking().then(response => {
                if (response.errors) {
                    this.$bvToast.toast(response.errors.message, {
                        title: 'Error',
                        variant: 'danger',
                        toaster: 'b-toaster-top-center'
                    });
                } else {
                    this.ranking = response.place;
                }
            }), userStatsService.getStats().then(response => {
                if (response.errors) {
                    this.$bvToast.toast(response.errors.message, {
                        title: 'Error',
                        variant: 'danger',
                        toaster: 'b-toaster-top-center'
                    });
                } else {
                    this.stats = response.statistics;
                }
            })]).then(() => {
                this.loading = false;
            });
        },

    }
</script>

<style scoped>

    .edit {
        width: 100%;
        text-align: right;
        padding-right: 25px;
    }

    .profile {
        margin-bottom: 20px;
    }

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
        margin-top: 20px;
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

    .stats-section {
        margin-top: 50px;
    }

    .left, .right {
        background: rgba(0, 0, 0, 0.4);
    }

    .right {
        border-radius: 0 15px 15px 0;
    }

    .left {
        border-radius: 15px 0 0 15px;
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

    button:active, button:focus {
        background: none!important;
        border: 2px solid var(--colour4)!important;
        box-shadow: none!important;
    }


    input {
        margin-top: 15px;
    }

</style>
