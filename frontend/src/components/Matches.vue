<template>
    <div class="matches">
        <ul>
            <li v-for="(match, index) in filteredMatches"
                v-bind:key="match.id"
                v-bind:not-last="isNotLast(index)"
                v-bind:first="isFirst(index)"
                v-on:click="showMatch(match.id)">
                <b-row v-if="showDate(index)">
                    <b-col class="matchesDate">
                        {{match.startDate.year}} - {{match.startDate.monthValue}} - {{match.startDate.dayOfMonth}}
                    </b-col>
                </b-row>
                <b-row class="matchRow">
                    <b-col class="leftTeam">
                        {{match.teamA.name}}
                        <b-avatar v-bind:src="match.teamA.url"/>
                    </b-col>
                    <b-col class="res" cols="1">
                        <a>{{match.realScoreA}} - {{match.realScoreB}}</a>
                    </b-col>
                    <b-col class="rightTeam">
                        <b-avatar v-bind:src="match.teamB.url"/>
                        {{match.teamB.name}}
                    </b-col>
                </b-row>
            </li>
        </ul>
    </div>
</template>

<script>
    export default {
        name: "Matches",
        props: {
            filteredMatches: {
                type: Array,
                required: true
            }
        },
        data: function(){
            return{
                dateBefore: {
                    year: null,
                    month: null,
                    day: null
                }
            }
        },

        methods: {
            isNotLast: function (index) {
                return !(index + 1 === this.filteredMatches.length) ? 1 : 0;
            },
            isFirst: function (index) {
                return index === 0 ? 1 : 0;
            },
            showMatch: function (matchId) {
                this.$router.push({path: `/match/${matchId}`});
            },
            showDate: function (index) {
                if(index === 0){
                    return true;
                }
                return !(this.filteredMatches[index].startDate.year === this.filteredMatches[index-1].startDate.year &&
                    this.filteredMatches[index].startDate.monthValue === this.filteredMatches[index-1].startDate.monthValue &&
                    this.filteredMatches[index].startDate.dayOfMonth === this.filteredMatches[index-1].startDate.dayOfMonth)
            }
        }
    }
</script>

<style scoped>
    .matches {
        margin-top: 60px;
        background: rgba(0, 0, 0, 0.4);
        border-radius: 15px;
    }

    ul {
        list-style: none;
        padding: 0;
        border-radius: 15px;
    }

    li {
        padding-top: 25px;
        padding-bottom: 25px;
    }

    li:hover {
        cursor: pointer;
        background: rgba(255, 255, 255, .1);
    }

    li[not-last="1"] {
        border-bottom: 1px solid #cfcfcf;
    }

    li[not-last="0"] {
        border-bottom-left-radius: 15px;
        border-bottom-right-radius: 15px;
    }

    li[first="1"] {
        border-top-left-radius: 15px;
        border-top-right-radius: 15px;
    }

    .res {
        position: relative;
        color: #cfcfcf;
        font-size: 20px;
    }

    a {
        position: absolute;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
        height: 50%;
        margin: auto;
    }

    .matchesDate{
        color: var(--colour5);
        font-size: 30px;
        padding-bottom: 20px;
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

</style>
