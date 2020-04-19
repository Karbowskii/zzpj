<template>
    <div>
        <ul>
            {{filteredMatches}}
            <li v-for="(match,index) in filteredMatches"
                v-bind:key="index">
                <b-row>
                    <b-col>
                        {{match.team1Name}}
                        <b-img v-bind:src="match.team1Logo"/>
                    </b-col>
                    <b-col>
                        {{match.result}}
                    </b-col>
                    <b-col>
                        <b-img v-bind:src="match.team2Logo"/>
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
        props: ["filteredMatches"],
        data: function () {
            return{
            }
        }
    }
</script>

<style scoped>

</style>