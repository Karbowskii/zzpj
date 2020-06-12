<template>
    <div>
        <h1>UPCOMING MATCHES</h1>

        <b-container>
            <b-row>
                <b-col>
                    <b-carousel
                            v-model="slide"
                            :interval="80000"
                            controls
                            indicators
                            img-height="1"
                            img-width="3"

                    >
                        <b-carousel-slide v-for="match in matches" :key="match.id">
                            <template v-slot:img>
                                <div class="carousel-element">
                                    <b-container>
                                        <b-row>
                                            <b-col cols="5">
                                                <b-img height="200" :src="match.teamA.url"></b-img>
                                            </b-col>
                                            <b-col cols="2">
                                                <h1>vs</h1>
                                            </b-col>
                                            <b-col cols="5">
                                                <b-img height="200" :src="match.teamB.url"></b-img>
                                            </b-col>
                                        </b-row>
                                    </b-container>
                                </div>
                            </template>
                        </b-carousel-slide>

                    </b-carousel>
                </b-col>
            </b-row>
        </b-container>

    </div>

</template>

<script>

    import {matchService} from "../App";

    export default {
        name: "Home",
        data() {
            return {
                slide: 0,
                matches: []
            }
        },
        created() {
            matchService.getClosestMatches().then((response) => {
                if (response.errors){
                    alert(response.errors.message);
                } else {
                    this.matches = response;
                }
            })
        }
    }
</script>

<style scoped>
    h1 {
        margin-top: 60px;
        color: white;
    }

    .carousel-element {
        margin-bottom: 50px;
    }

</style>
