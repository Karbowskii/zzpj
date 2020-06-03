<template>
    <div class="page-not-found"></div>
</template>

<script>
    export default {
        name: "PageNotFound",
        created() {
            this.$router.replace('/');
        }

    }
</script>

<style scoped>

</style>
