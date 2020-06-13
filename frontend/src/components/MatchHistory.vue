<template>
    <div>
        <div v-if="isLoaded" class="matchHistory">
            <a class="history">HISTORY</a>
            <ul>
                <li v-for="(bet, index) in bets"
                    v-bind:key="bet.id"
                    v-bind:not-last="isNotLast(index)">
                    <b-row>
                        <b-col cols="1" class="bet-result">
                            <a v-if="bet.selectedA">
                                <a v-if="bet.match.whichTeamWon===1" class="win">+{{bet.coins * bet.match.stakeA -
                                    bet.coins}}</a>
                                <a v-else-if="bet.match.whichTeamWon===2" class="lose">-{{bet.coins}}</a>
                                <a v-else>{{bet.coins}}</a>
                            </a>
                        </b-col>
                        <b-col class="leftTeam">
                            {{bet.match.teamA.name}}
                            <b-avatar v-bind:src="bet.match.teamA.url"/>
                        </b-col>
                        <b-col class="res" cols="1">
                            <a>{{bet.match.realScoreA}} - {{bet.match.realScoreB}}</a>
                        </b-col>
                        <b-col class="rightTeam">
                            <b-avatar v-bind:src="bet.match.teamB.url"/>
                            {{bet.match.teamB.name}}
                        </b-col>
                        <b-col cols="1" class="bet-result">
                            <a v-if="!bet.selectedA">
                                <a v-if="bet.match.whichTeamWon===2" class="win">+{{bet.coins * bet.match.stakeB -
                                    bet.coins}}</a>
                                <a v-else-if="bet.match.whichTeamWon===1" class="lose">-{{bet.coins}}</a>
                                <a v-else>{{bet.coins}}</a>
                            </a>
                        </b-col>
                    </b-row>
                </li>
            </ul>
        </div>
        <div v-else>
            <h1>LOADING</h1>
        </div>
    </div>
</template>

<script>
    import {betService} from "../App";

    const _ = require('lodash');

    export default {
        name: "MatchHistory",
        data: function () {
            return {
                bets: [],
                isLoaded: false
            }
        },
        methods: {
            isNotLast: function (index) {
                return !(index + 1 === this.bets.length) ? 1 : 0;
            }
        },
        created() {
            betService.getBetsForUser(this.$store.state.user.id).then(data => {
                this.bets = _.sortBy(data, function (o) {
                    return [o.match.startDate.year, o.match.startDate.dayOfYear]
                }, ['desc']);
                this.isLoaded = true;
            })
        }
    }
</script>

<style scoped>
    .matchHistory {
        margin-top: 50px;
        background: rgba(0, 0, 0, 0.4);
        border-radius: 15px;
    }

    .history, .history:hover {
        font-size: 60px;
        color: var(--colour4);
    }

    ul {
        list-style: none;
        padding: 0;
        border-radius: 15px;
    }

    li {
        padding-top: 25px;
        padding-bottom: 25px;
        border-top: 1px solid #cfcfcf;
    }

    li:hover {
        background: rgba(255, 255, 255, .1);
    }

    li[not-last="0"] {
        border-bottom-left-radius: 15px;
        border-bottom-right-radius: 15px;
    }

    .res {
        position: relative;
        color: #cfcfcf;
        font-size: 20px;
        margin-top: 10px;
    }

    .leftTeam {
        text-align: right;
        color: #cfcfcf;
        font-size: 20px;
    }

    .rightTeam {
        text-align: left;
        color: #cfcfcf;
        font-size: 20px;
    }

    .bet-result {
        color: var(--colour4);
        text-align: center;
        margin-top: 10px;
        font-size: 20px;
    }

    .win, .win:hover {
        color: var(--colour5);
    }

    .lose, .lose:hover {
        color: #e21f25;
    }

</style>
