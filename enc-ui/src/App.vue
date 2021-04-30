<template>
  <div id="app">
    <BaseApp/>
  </div>
</template>

<script>
import BaseApp from './components/BaseApp'

export default {
  name: 'App',
  components: {
    BaseApp
  }
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 0px;
}
</style>
