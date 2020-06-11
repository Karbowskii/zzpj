<template>
    <div class="match">
        <b-container>
            <b-row>
                <b-col class="leftTeam" cols="3">
                    <div class="text-center">
                        <b-avatar v-bind:src="match.teamA.url"/>
                        <p>{{match.teamA.name}}</p>
                        <b-button v-if="match.status === 'NOT_STARTED'" @click="calculateBetStake(match.stakeA, true)">
                            Bet
                        </b-button>
                    </div>
                </b-col>
                <b-col class="res" cols="6">
                    <div>
                        <a>{{match.realScoreA}} - {{match.realScoreB}}</a>
                        <b-progress>
                            <b-progress-bar :max="match.stakeA + match.stakeB" :value="match.stakeA"></b-progress-bar>
                        </b-progress>
                        <a class="left-ratio">{{match.stakeA.toFixed(2)}}</a>
                        <a class="right-ratio">{{match.stakeB.toFixed(2)}}</a>
                    </div>
                </b-col>
                <b-col class="rightTeam" cols="3">
                    <div class="text-center">
                        <b-avatar v-bind:src="match.teamB.url"/>
                        <p>{{match.teamB.name}}</p>
                        <b-button v-if="match.status === 'NOT_STARTED'" @click="calculateBetStake(match.stakeB, false)">
                            Bet
                        </b-button>
                    </div>
                </b-col>
            </b-row>
        </b-container>
        <betting-modal v-bind:betStake="betStake" v-bind:is-a-selected="isASelected"></betting-modal>
    </div>
</template>

<script>
    import BettingModal from "./BettingModal";
    import {matchService} from "../App";

    export default {
        name: "Match",
        components: {BettingModal},
        data: function () {
            return {
                match: null,
                betStake: 0,
                isASelected: false
            }
        },
        created() {
            this.id = this.$route.params.id;
            matchService.getMatchById(this.$route.params.id).then((response) => {
                if (response.errors) {
                    alert("Error");
                } else {
                    this.match = response;
                }
            })

        },
        methods: {
            calculateBetStake: function (stake, isASelected) {
                this.betStake = stake;
                this.isASelected = isASelected;
                if (this.$store.getters.isAuthorized) {
                    this.showModal();
                } else {
                    this.$router.push('/login');
                }
            },
            showModal: function () {
                this.$bvModal.show('betting-modal');
            }
        },

    }
</script>

<style scoped>

    .match {
        margin-top: 70px;
        margin-bottom: 40px;
    }

    .res {
        position: relative;
        color: #cfcfcf;
        font-size: 80px;
    }

    .res > div {
        background: rgba(0, 0, 0, 0.4);
        padding: 0 20px 40px 20px;
        border-radius: 15px;

    }

    .leftTeam {
        color: #cfcfcf;
        font-size: 50px;
    }

    .rightTeam {
        color: #cfcfcf;
        font-size: 50px;
    }

    .leftTeam div, .rightTeam div {
        background: rgba(0, 0, 0, 0.4);
        padding: 20px 0;
        border-radius: 15px;
    }

    .left-ratio {
        font-size: 20px;
        position: absolute;
        left: 35px;
    }

    .right-ratio {
        font-size: 20px;
        position: absolute;
        right: 35px;
    }

    .progress .progress-bar {
        background: #63f0e4 !important;
    }

    .progress {
        background: var(--colour6) !important;
    }

    button {
        color: var(--colour4);
        font-size: 20px;
        background: none;
        font-weight: 700;
        position: relative;
        border: 4px solid var(--colour5);
        width: 100px !important;
    }

    button::before, button::after {
        content: "";
        position: absolute;
        width: 14px;
        height: 4px;
        background: rgb(10, 15, 21);
        transform: skewX(50deg);
        transition: .4s;
    }

    button:before {
        top: -4px;
        left: 10%;
    }

    button:after {
        bottom: -4px;
        right: 10%;
    }

    .btn:hover::before {
        left: 80%;
    }

    .btn:hover::after {
        right: 80%;
    }

    .btn:hover, .btn:active, .btn:focus {
        background: none !important;
        border: 4px solid var(--colour5) !important;
        box-shadow: none;
    }

</style>
