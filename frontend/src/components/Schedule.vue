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
                this.allMatches = response;
            })
        }
    }
</script>

<style scoped>
    .matches {
        background: rgba(0, 0, 0, 0.4);
        border-radius: 15px;
    }
</style>
