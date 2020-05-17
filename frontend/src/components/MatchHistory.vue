<template>
    <div class="matchHistory">
        <a class="history">HISTORY</a>
        <ul>
            <li v-for="(bet, index) in bets"
                v-bind:key="index"
                v-bind:not-last="isNotLast(index)">
                <b-row>
                    <b-col cols="2" class="bet-result">
                        <a v-if="bet.team===1">
                            <a v-if="bet.result===true" class="win">+{{bet.value*bet.stake-bet.value}}</a>
                            <a v-else-if="bet.result===false" class="lose">-{{bet.value}}</a>
                            <a v-else>{{bet.value}}</a>
                        </a>
                    </b-col>
                    <b-col class="leftTeam">
                        {{bet.match.team1Name}}
                        <b-avatar v-bind:src="bet.match.team1Logo"/>
                    </b-col>
                    <b-col class="res" cols="1">
                        <a>{{bet.match.result}}</a>
                    </b-col>
                    <b-col class="rightTeam">
                        <b-avatar v-bind:src="bet.match.team2Logo"/>
                        {{bet.match.team2Name}}
                    </b-col>
                    <b-col cols="2" class="bet-result">
                        <a v-if="bet.team===2">
                            <a v-if="bet.result===true" class="win">+{{bet.value*bet.stake-bet.value}}</a>
                            <a v-else-if="bet.result===false" class="lose">-{{bet.value}}</a>
                            <a v-else>{{bet.value}}</a>
                        </a>
                    </b-col>
                </b-row>
            </li>
        </ul>
    </div>
</template>

<script>
    export default {
        name: "MatchHistory",
        data: function () {
            return {
                bets: [{
                    match: {
                        id: '1',
                        result: "3:0",
                        team1Name: "G2",
                        team1Logo: "https://cybersport.pl/wp-content/uploads/2019/01/g2_logo2019.png",
                        team2Name: "Fnatic",
                        team2Logo: "https://gamepedia.cursecdn.com/lolesports_gamepedia_en/thumb/f/fc/Fnaticlogo_square.png/1200px-Fnaticlogo_square.png",
                        date: new Date("2020-04-19"),
                        status: "finished"
                    },
                    value: 30,
                    stake: 2.25,
                    team: 1,
                    result: true
                },
                    {
                        match: {
                            id: '1',
                            result: "3:0",
                            team1Name: "G2",
                            team1Logo: "https://cybersport.pl/wp-content/uploads/2019/01/g2_logo2019.png",
                            team2Name: "Fnatic",
                            team2Logo: "https://gamepedia.cursecdn.com/lolesports_gamepedia_en/thumb/f/fc/Fnaticlogo_square.png/1200px-Fnaticlogo_square.png",
                            date: new Date("2020-04-19"),
                            status: "finished"
                        },
                        value: 30,
                        stake: 2.25,
                        team: 2,
                        result: false
                    },
                    {
                        match: {
                            id: '1',
                            result: "0:0",
                            team1Name: "G2",
                            team1Logo: "https://cybersport.pl/wp-content/uploads/2019/01/g2_logo2019.png",
                            team2Name: "Fnatic",
                            team2Logo: "https://gamepedia.cursecdn.com/lolesports_gamepedia_en/thumb/f/fc/Fnaticlogo_square.png/1200px-Fnaticlogo_square.png",
                            date: new Date("2020-04-19"),
                            status: "not started"
                        },
                        value: 200,
                        stake: 2.25,
                        team: 1,
                        result: null
                    },
                    {
                        match: {
                            id: '1',
                            result: "0:2",
                            team1Name: "G2",
                            team1Logo: "https://cybersport.pl/wp-content/uploads/2019/01/g2_logo2019.png",
                            team2Name: "Fnatic",
                            team2Logo: "https://gamepedia.cursecdn.com/lolesports_gamepedia_en/thumb/f/fc/Fnaticlogo_square.png/1200px-Fnaticlogo_square.png",
                            date: new Date("2020-04-19"),
                            status: "finished"
                        },
                        value: 300,
                        stake: 2.25,
                        team: 2,
                        result: true
                    }]
            }
        },
        methods: {
            isNotLast: function (index) {
                return !(index + 1 === this.bets.length) ? 1 : 0;
            }
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
