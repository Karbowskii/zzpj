<template>
    <div class="loading">
        <b-container>
            <b-row align-h="center" class="pacman">
                <b-col cols="2"></b-col>
                <b-col cols="4">
                    <PacmanLoader class="pacmanik" :size="40" color="#C5C6C7"></PacmanLoader>
                </b-col>
            </b-row>
        </b-container>
    </div>
</template>

<script>
    export default {
        name: "Loading"
    }
</script>

<style scoped>
    .pacman{
        margin-top: 20%;
    }
    /*.pacmanik{*/
    /*    margin-left: 100px;*/
    /*}*/
</style>