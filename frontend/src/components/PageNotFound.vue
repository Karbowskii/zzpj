<template>
    <div class="page-not-found">
        <h1>Page not found!</h1>
        <b-img :src="require('../assets/sadge.png')"></b-img>
    </div>
</template>

<script>
    export default {
        name: "PageNotFound",
    }
</script>

<style scoped>

    h1 {
        color: var(--colour4);
    }

    .page-not-found {
        margin-top: 30px;
    }

</style>
