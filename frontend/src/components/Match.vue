<template>
    <div class="match">
        <b-container>
            <b-row>
                <b-col class="leftTeam" cols="2">
                    <div class="text-center">
                        <b-avatar v-bind:src="team1Logo"/>
                        <p>{{team1Name}}</p>
                        <b-button v-b-modal.betting-modal>Bet</b-button>
                    </div>
                </b-col>
                <b-col class="res" cols="8">
                    <a>{{result}}</a>
                    <b-progress>
                        <b-progress-bar :max="100" :value="betRatio"></b-progress-bar>
                    </b-progress>
                    <a class="left-ratio">{{betRatio}}%</a>
                    <a class="right-ratio">{{100-betRatio}}%</a>
                </b-col>
                <b-col class="rightTeam" cols="2">
                    <div class="text-center">
                        <b-avatar v-bind:src="team2Logo"/>
                        <p>{{team2Name}}</p>
                        <b-button v-b-modal.betting-modal>Bet</b-button>
                    </div>
                </b-col>
            </b-row>
        </b-container>
        <betting-modal></betting-modal>
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
                result: "1:3",
                team1Name: "G2",
                team1Logo: "https://cybersport.pl/wp-content/uploads/2019/01/g2_logo2019.png",
                team2Name: "Fnatic",
                team2Logo: "https://gamepedia.cursecdn.com/lolesports_gamepedia_en/thumb/f/fc/Fnaticlogo_square.png/1200px-Fnaticlogo_square.png",
                date: new Date("2020-04-19"),
                status: "finished",
                betRatio: 60
            }
        },
        created() {
            this.id = this.$route.params.id
        }
    }
</script>

<style scoped>
    /*    .container{
            margin-left: 0!important;
            margin-right: 0!important;

            width: 100% !important;
        }*/
    .match {
        margin-top: 130px;
    }

    .res {
        position: relative;
        color: #cfcfcf;
        font-size: 80px;
    }

    .leftTeam {
        color: #cfcfcf;
        font-size: 50px;
        display: flex;
    }

    .rightTeam {
        direction: rtl;
        display: flex;
        color: #cfcfcf;
        font-size: 50px;
    }

    .left-ratio {
        font-size: 20px;
        position: absolute;
        left: 15px;
    }

    .right-ratio {
        font-size: 20px;
        position: absolute;
        right: 15px;
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
        width: 100px!important;
    }

    button::before, button::after {
        content: "";
        position: absolute;
        width: 14px;
        height: 4px;
        background: #1d232e;
        transform: skewX(50deg);
        transition: .4s;
    }

    button:before{
        top: -4px;
        left: 10%;
    }

    button:after{
        bottom: -4px;
        right: 10%;
    }

    .btn:hover::before{
        left: 80%;
    }

    .btn:hover::after{
        right: 80%;
    }

    .btn:hover,.btn:active,.btn:focus{
        background: none!important;
        border: 4px solid var(--colour5)!important;
        box-shadow: none;
    }



</style>