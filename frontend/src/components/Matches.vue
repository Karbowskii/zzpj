<template>
    <div id="matches">
        <ul>
            <li v-for="(match, index) in filteredMatches"
                v-bind:key="match.id"
                v-bind:last="isNotLast(index)"
                v-on:click="showMatch(match.id)">
                    <b-row>
                        <b-col class="leftTeam">
                            {{match.team1Name}}
                            <b-avatar v-bind:src="match.team1Logo"/>
                        </b-col>
                        <b-col class="res" cols="1">
                            <a>{{match.result}}</a>
                        </b-col>
                        <b-col class="rightTeam">
                            <b-avatar v-bind:src="match.team2Logo"/>
                            {{match.team2Name}}
                        </b-col>
                    </b-row>
            </li>
        </ul>
    </div>
</template>

<script>
    export default {
        name: "Matches",
        props: {filteredMatches: {
                type: Array,
                required: true
            }
        },
        data: function () {
            return{
            }
        },
       methods:{
            isNotLast: function (index) {
                return !(index + 1 === this.filteredMatches.length);
            },
            showMatch: function (matchId) {
                this.$router.push({ path: `/match/${matchId}`})
            }

        }
    }
</script>

<style scoped>
    #matches {
        margin-top: 20px;
    }

    ul {
        list-style: none;
        padding: 0;
    }

    li {
        padding-top: 15px;
        padding-bottom: 15px;
        /*border-top: 1px solid #cfcfcf;*/
    }
    
    li:hover{
        cursor: pointer;
    }

    li[last="true"]{
        border-bottom: 1px solid #cfcfcf;
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
