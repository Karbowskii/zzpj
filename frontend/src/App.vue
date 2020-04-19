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
  text-align: center;
  height: 100%;
/*  background: #4e4f4e;*/
}
  html, body{
    height: 100%;
    background: url("assets/89503.jpg") no-repeat center center fixed;
    background-size: cover;
  }
</style>
