<template>
    <div>
        <loading v-if="loading"></loading>
        <b-container v-else>
            <b-row>
                <!--Matches-->
                <b-col cols="9">
                    <Matches :filtered-matches="filteredMatches"></Matches>
                </b-col>

                <!--Filter-->
                <b-col cols="3">
                    <div class="filterPanel">
                        <div class="checkboxik form-check">
                            <input class="form-check-input" type="checkbox" id="pastMatchesCheckbox"
                                   v-model="filterOptions.pastMatchesShown">
                            <label class="form-check-label" for="pastMatchesCheckbox">
                                Finished matches
                            </label>
                        </div>
                        <div class="checkboxik form-check">
                            <input class="form-check-input" type="checkbox" id="upcomingMatchesCheckbox"
                                   v-model="filterOptions.upcomingMatchesShown">
                            <label class="form-check-label" for="upcomingMatchesCheckbox">
                                Upcoming matches
                            </label>
                        </div>
                        <div class="checkboxik form-check">
                            <input class="form-check-input" type="radio" name="sort" id="sortByDateAsc"
                                   v-model="filterOptions.sortByDate" value="asc">
                            <label class="form-check-label" for="sortByDateAsc">
                                Sort from oldest
                            </label>
                        </div>
                        <div class="checkboxik form-check">
                            <input class="form-check-input" type="radio" name="sort" id="sortByDateDesc"
                                   v-model="filterOptions.sortByDate" value="desc">
                            <label class="form-check-label" for="sortByDateDesc">
                                Sort from newest
                            </label>
                        </div>
                        <div class="search">
                            <input id="searching" placeholder="Search" v-model="filterOptions.searchName">
                        </div>
                        <b-button @click="filter">Filter</b-button>
                    </div>
                </b-col>
            </b-row>
        </b-container>
    </div>
</template>

<script>
    import Matches from "./Matches";
    import {matchService} from "../App";
    import Loading from "./Loading";

    const _ = require('lodash');

    export default {
        name: "Schedule",
        components: {Loading, Matches},
        data: function () {
            return {
                allMatches: [],
                filteredMatches: [],
                filterOptions: {
                    searchName: "",
                    pastMatchesShown: true,
                    upcomingMatchesShown: true,
                    sortByDate: 'asc',
                },
                loading: true
            }
        },
        created() {
            matchService.getAllMatches().then((response) => {
                if (response.errors) {
                    this.$bvToast.toast(response.errors.message, {
                        title: 'Error',
                        variant: 'danger',
                        toaster: 'b-toaster-top-center'
                    });
                } else {
                    this.allMatches = _.orderBy(response, function (o) {
                        return [o.startDate.year, o.startDate.dayOfYear, o.startDate.hour]
                    }, 'asc');
                    this.filteredMatches = this.allMatches;
                }

                this.loading = false
            })
        },
        methods: {
            filter: function () {
                this.loading = true
                this.filteredMatches = _.orderBy(this.allMatches, function (o) {
                    return [o.startDate.year, o.startDate.dayOfYear, o.startDate.hour]
                }, this.filterOptions.sortByDate);

                if (!this.filterOptions.pastMatchesShown)
                    this.filteredMatches = _.filter(this.filteredMatches, function (o) {
                        return o.status !== 'FINISHED'
                    })

                if (!this.filterOptions.upcomingMatchesShown)
                    this.filteredMatches = _.filter(this.filteredMatches, function (o) {
                        return o.status === 'FINISHED'
                    })

                let teamName = this.filterOptions.searchName
                if (teamName !== "")
                    this.filteredMatches = _.filter(this.filteredMatches, function (o) {
                        return (o.teamA.name.includes(teamName) || o.teamB.name.includes(teamName))
                    })
                this.loading = false
            }
        }
    }
</script>

<style scoped>
    .filterPanel {
        margin-top: 60px;
        margin-left: 60px;
        padding-left: 25px;
        padding-right: 25px;
        padding-bottom: 20px;
        background: rgba(0, 0, 0, 0.4);
        border-radius: 15px;
        position: fixed;
    }

    .checkboxik {
        font-size: 20px;
        color: #cfcfcf;
        text-align: left;
        /*padding-left: 17%;*/
        padding-top: 20px;
    }

    .search {
        padding-top: 40px;
        padding-bottom: 40px;
    }

    label {
        padding-left: 10px;
    }

    button {
        border: 2px solid var(--colour4);
        background: none;
        color: #b9b9b9;
        padding-left: 20px;
        padding-right: 20px;
    }

    button:hover {
        border: 2px solid var(--colour4);
        background: none;
        text-shadow: 0 0 5px var(--colour5);
    }
</style>
