<template>
    <div>
        <b-container>
            <b-row>
                <!--Matches-->
                <b-col cols="9">
                    <Matches :filtered-matches="allMatches"></Matches>
                </b-col>

                <!--Filter-->
                <b-col cols="3">

                </b-col>
            </b-row>
        </b-container>
    </div>
</template>

<script>
    import Matches from "./Matches";

    export default {
        name: "Schedule",
        components: {Matches},
        data: function () {
            return {
                allMatches: [{
                    id: '1',
                    result: "3:0",
                    team1Name: "G2",
                    team1Logo: "https://cybersport.pl/wp-content/uploads/2019/01/g2_logo2019.png",
                    team2Name: "Fnatic",
                    team2Logo: "https://gamepedia.cursecdn.com/lolesports_gamepedia_en/thumb/f/fc/Fnaticlogo_square.png/1200px-Fnaticlogo_square.png",
                    date: new Date("2020-04-19"),
                    status: "finished"
                },
                    {
                        id: '2',
                        result: "1:3",
                        team1Name: "G2",
                        team1Logo: "https://cybersport.pl/wp-content/uploads/2019/01/g2_logo2019.png",
                        team2Name: "Fnatic",
                        team2Logo: "https://gamepedia.cursecdn.com/lolesports_gamepedia_en/thumb/f/fc/Fnaticlogo_square.png/1200px-Fnaticlogo_square.png",
                        date: new Date("2020-04-19"),
                        status: "finished"
                    },
                    {
                        id: '3',
                        result: "1:3",
                        team1Name: "G2",
                        team1Logo: "https://cybersport.pl/wp-content/uploads/2019/01/g2_logo2019.png",
                        team2Name: "Fnatic",
                        team2Logo: "https://gamepedia.cursecdn.com/lolesports_gamepedia_en/thumb/f/fc/Fnaticlogo_square.png/1200px-Fnaticlogo_square.png",
                        date: new Date("2020-04-19"),
                        status: "finished"
                    },
                    {
                        id: '4',
                        result: "1:3",
                        team1Name: "G2",
                        team1Logo: "https://cybersport.pl/wp-content/uploads/2019/01/g2_logo2019.png",
                        team2Name: "Fnatic",
                        team2Logo: "https://gamepedia.cursecdn.com/lolesports_gamepedia_en/thumb/f/fc/Fnaticlogo_square.png/1200px-Fnaticlogo_square.png",
                        date: new Date("2020-04-19"),
                        status: "finished"
                    },
                    {
                        id: '5',
                        result: "1:3",
                        team1Name: "G2",
                        team1Logo: "https://cybersport.pl/wp-content/uploads/2019/01/g2_logo2019.png",
                        team2Name: "Fnatic",
                        team2Logo: "https://gamepedia.cursecdn.com/lolesports_gamepedia_en/thumb/f/fc/Fnaticlogo_square.png/1200px-Fnaticlogo_square.png",
                        date: new Date("2020-04-19"),
                        status: "finished"
                    }
                    ,
                    {
                        id: '6',
                        result: "1:3",
                        team1Name: "G2",
                        team1Logo: "https://cybersport.pl/wp-content/uploads/2019/01/g2_logo2019.png",
                        team2Name: "Fnatic",
                        team2Logo: "https://gamepedia.cursecdn.com/lolesports_gamepedia_en/thumb/f/fc/Fnaticlogo_square.png/1200px-Fnaticlogo_square.png",
                        date: new Date("2020-04-19"),
                        status: "finished"
                    },
                    {
                        id: '7',
                        result: "1:3",
                        team1Name: "G2",
                        team1Logo: "https://cybersport.pl/wp-content/uploads/2019/01/g2_logo2019.png",
                        team2Name: "Fnatic",
                        team2Logo: "https://gamepedia.cursecdn.com/lolesports_gamepedia_en/thumb/f/fc/Fnaticlogo_square.png/1200px-Fnaticlogo_square.png",
                        date: new Date("2020-04-19"),
                        status: "finished"
                    },
                    {
                        id: '8',
                        result: "1:3",
                        team1Name: "G2",
                        team1Logo: "https://cybersport.pl/wp-content/uploads/2019/01/g2_logo2019.png",
                        team2Name: "Fnatic",
                        team2Logo: "https://gamepedia.cursecdn.com/lolesports_gamepedia_en/thumb/f/fc/Fnaticlogo_square.png/1200px-Fnaticlogo_square.png",
                        date: new Date("2020-04-19"),
                        status: "finished"
                    },
                    {
                        id: '9',
                        result: "0:0",
                        team1Name: "G2",
                        team1Logo: "https://cybersport.pl/wp-content/uploads/2019/01/g2_logo2019.png",
                        team2Name: "Fnatic",
                        team2Logo: "https://gamepedia.cursecdn.com/lolesports_gamepedia_en/thumb/f/fc/Fnaticlogo_square.png/1200px-Fnaticlogo_square.png",
                        date: new Date("2020-04-19"),
                        status: "planned"
                    }
                ]
            }
        }
    }
</script>

<style scoped>
    #matches {
        background: rgba(0, 0, 0, 0.4);
        border-radius: 15px;
    }
</style>
