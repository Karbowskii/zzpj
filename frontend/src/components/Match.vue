<template>
    <div class="match">
        <b-container>
            <b-row>
                <b-col class="leftTeam" cols="3">
                    <div class="text-center">
                        <b-avatar v-bind:src="team1Logo"/>
                        <p>{{team1Name}}</p>
                        <b-button v-if="status!=='finished'" @click="calculateBetStake(betRatio)" v-b-modal.betting-modal>Bet</b-button>
                    </div>
                </b-col>
                <b-col class="res" cols="6">
                    <div>
                        <a>{{result}}</a>
                        <b-progress>
                            <b-progress-bar :max="100" :value="betRatio"></b-progress-bar>
                        </b-progress>
                        <a class="left-ratio">{{betRatio}}%</a>
                        <a class="right-ratio">{{100-betRatio}}%</a>
                    </div>
                </b-col>
                <b-col class="rightTeam" cols="3">
                    <div class="text-center">
                        <b-avatar v-bind:src="team2Logo"/>
                        <p>{{team2Name}}</p>
                        <b-button v-if="status!=='finished'" @click="calculateBetStake(100-betRatio)"
                                  v-b-modal.betting-modal>Bet
                        </b-button>
                    </div>
                </b-col>
            </b-row>
        </b-container>
        <betting-modal v-bind:betStake="betStake"></betting-modal>
    </div>
</template>

<script>
    import BettingModal from "./BettingModal";

    export default {
        name: "Match",
        components: {BettingModal},
        data: function () {
            return {
                id: null,
                result: "0:0",
                team1Name: "G2",
                team1Logo: "https://cybersport.pl/wp-content/uploads/2019/01/g2_logo2019.png",
                team2Name: "Fnatic",
                team2Logo: "https://gamepedia.cursecdn.com/lolesports_gamepedia_en/thumb/f/fc/Fnaticlogo_square.png/1200px-Fnaticlogo_square.png",
                date: new Date("2020-04-19"),
                status: "planned",
                betRatio: 60,
                betStake: null
            }
        },
        created() {
            this.id = this.$route.params.id
        },
        methods: {
            calculateBetStake: function (teamBetRatio) {
                this.betStake = 100 / teamBetRatio
            }
        }
    }
</script>

<style scoped>

    .match {
        margin-top: 130px;
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
