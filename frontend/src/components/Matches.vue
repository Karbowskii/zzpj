<template>
    <div class="matches">
        <ul>
            <li v-for="(match, index) in filteredMatches"
                v-bind:key="match.id"
                v-bind:not-last="isNotLast(index)"
                v-bind:first="isFirst(index)"
                v-on:click="showMatch(match.id)">
                <b-row>
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
        methods: {
            isNotLast: function (index) {
                return !(index + 1 === this.filteredMatches.length) ? 1 : 0;
            },
            isFirst: function (index) {
                return index === 0 ? 1 : 0;
            },
            showMatch: function (matchId) {
                this.$router.push({path: `/match/${matchId}`});
            }
        }
    }
</script>

<style scoped>
    .matches {
        margin-top: 60px;
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
