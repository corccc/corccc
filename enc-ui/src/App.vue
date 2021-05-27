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
  font-family: "Arial","Microsoft YaHei","黑体","宋体",sans-serif;
  font-weight: 700;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: left;
  color: #000000;
  margin-top: 0px;
}
.el-button {
  font-weight:700;
  background-color: #5bab82;
  border-color: #5bab82;
  color: #fff;
}
.el-button--primary.is-active,
.el-button--primary:active {
  background: #5B9682;
  border-color: #5B9682;
  color: #fff;
}
.el-button--primary:focus,
.el-button--primary:hover {
  background: #5B9682;
  border-color: #5B9682;
  color: #fff;
}

</style>
