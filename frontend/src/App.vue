<template>
  <div id="app">
    <NavigationPanel></NavigationPanel>
    <router-view/>
  </div>
</template>

<script>
import NavigationPanel from "./components/NavigationPanel";

export default {
  name: 'App',
  components: {NavigationPanel}
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
}
</style>
