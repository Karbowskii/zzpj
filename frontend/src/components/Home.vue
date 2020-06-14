<template>
    <div>
        <h1 class="display-1">UPCOMING MATCHES</h1>
        <loading v-if="loading"></loading>
        <b-container v-else>
            <b-row>
                <b-col>
                    <b-carousel
                            v-model="slide"
                            :interval="5000"
                            controls
                            indicators
                            img-height="1"
                            img-width="3"
                            v-on:sliding-start="startSliding"
                            v-on:sliding-end="endSliding"

                    >
                        <b-carousel-slide v-for="match in matches" :key="match.id">
                            <template v-slot:img>
                                <div class="carousel-element" v-on:click="goToMatch(match.id)">
                                    <b-container>
                                        <b-row>
                                            <b-col cols="5">
                                                <div class="img-wrapper">
                                                    <b-img height="200" :src="match.teamA.url"></b-img>
                                                </div>
                                            </b-col>
                                            <b-col cols="2">
                                                <transition name="vs">
                                                    <h1 v-if="!isSliding" class="display-1">vs</h1>
                                                </transition>
                                            </b-col>
                                            <b-col cols="5">
                                                <div class="img-wrapper">
                                                    <b-img height="200" :src="match.teamB.url"></b-img>
                                                </div>
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
    import Loading from "./Loading";

    export default {
        name: "Home",
        components: {Loading},
        data() {
            return {
                slide: 0,
                matches: [],
                isSliding: false,
                loading: true
            }
        },
        created() {
            matchService.getClosestMatches().then((response) => {
                if (response.errors) {
                    this.$bvToast.toast(response.errors.message, {
                        title: 'Error',
                        variant: 'danger',
                        toaster: 'b-toaster-top-center'
                    });
                } else {
                    this.matches = response;
                }
                this.loading = false;
            })
        },
        methods: {
            goToMatch(matchId) {
                this.$router.push({path: `/match/${matchId}`});
            },
            startSliding() {
                this.isSliding = true;
            },
            endSliding() {
                this.isSliding = false;
            }
        }
    }
</script>

<style scoped>
    h1 {
        margin-top: 50px;
        color: white;
        margin-bottom: 40px;
    }

    .carousel-element {
        margin-bottom: 60px;
        cursor: pointer;
    }

    img {
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
        margin: 0;
    }

    .img-wrapper {
        position: relative;
        height: 100%;
        min-height: 220px;
    }

    .carousel {
        padding-top: 20px;
        border-radius: 1em;
        background: rgba(255, 255, 255, 0.34);
        box-shadow: 0 0 15px 15px #2d3b4e;
        margin-bottom: 20px;
    }

    .vs-enter-active, .vs-leave-active {
        transition: opacity 0.3s;
    }

    .vs-enter, .vs-leave-to {
        opacity: 0;
    }

</style>
