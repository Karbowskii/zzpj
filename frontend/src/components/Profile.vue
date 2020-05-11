<template>
    <div class="profile">
        <b-container>
            <b-row align-h="center">
                <b-col cols=8>
                    <div class="profile-panel">
                        <div>
                            <div class="profile-icon"><b-img :src="profile.icon" height="250px" width="250px"></b-img></div>
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
                        </div>
                    </div>
                </b-col>
            </b-row>
            <b-row align-h="center">
                <b-col cols=8>
                    <match-history></match-history>
                </b-col>
            </b-row>
        </b-container>
    </div>
</template>

<script>

    import MatchHistory from "./MatchHistory";
    export default {
        name: "Profile",
        components: {MatchHistory},
        data: function () {
            return{
                profile:{
                    nick: 'Arturito',
                    firstName: 'Artur',
                    lastName: 'Karbonara',
                    lvl: 1,
                    exp: 5,
                    expToNextLvl: 20,
                    tokens: 21,
                    icon: require('../assets/profileIcon.png'),
                    description: ''
                }
            }
        }
    }
</script>

<style scoped>
    .profile-panel{
        margin-top: 60px;
        padding-top: 25px;
        padding-bottom: 25px;
        padding-left: 25px;
        text-align: left;
        display: flex;
        background: rgba(0, 0, 0, .2);
        border-radius: 15px;
    }
    .profile-icon{
        background: linear-gradient(to bottom left, var(--colour5) 20%,var(--colour8));
        border: none;
        position: relative;
        height: 250px;
        width: 250px;
        z-index: 10000000;
        border-radius: 30%;
    }

    div.profile-icon:after{
        content: "";
        position: absolute;
        padding: 10px;
        background:  #1F2833;
        top: 5px;
        bottom: 5px;
        left: 5px;
        right: 5px;
        border-radius: 30%;
        z-index: -1;
    }

    .nick-name{
        color: var(--colour5);
        margin-left: 40px;
        margin-top: 10px;
        font-size: 40px;
    }

    .name{
        font-size: 25px!important;
        color: var(--colour4) !important;
    }

    .lvl{
        text-align: center;
        color: var(--colour8);
        font-size: 30px;
    }

    .progress{
        height: 15px;
        /*margin-top: 20px;*/
        background-color: #d6d8db!important;
        margin-right: 0;
        min-width: 80px;
    }

    .progress .progress-bar{
        background: #b600b9 !important;
    }




</style>