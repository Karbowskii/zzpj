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
    import {matchService} from "../App";
    const _ = require('lodash');

    export default {
        name: "Schedule",
        components: {Matches},
        data: function () {
            return {
                allMatches: []
            }
        },
        created() {
            matchService.getAllMatches().then((response) => {
                this.allMatches = _.sortBy(response, function (o) {
                    return [o.startDate.year, o.startDate.dayOfYear, o.startDate.hour]
                }, ['asc']);
            })
        }
    }
</script>

<style scoped>
</style>
