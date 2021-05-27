<template>
  <el-container>
    <el-header> <Header/> </el-header>
    <el-container>
      <el-aside width="150px"> <LeftAside/> </el-aside>
      <el-main> <Main/> </el-main>
    </el-container>
  </el-container>
</template>

<script>
  import Header from './Header'
  import LeftAside from "./LeftAside"
  import Main from "./Main"
  export default {
    name: "BaseApp",
    components: {
      LeftAside,
      Header,
      Main,
    }
  }
</script>

<style>
  .el-header {
    background-color: #FFFFFF;
    height: 100px;
  }
  .el-aside {
    background-color: #FFFFFF;
    text-align: center;
  }
  .el-main {
    background-color: #FFFFFF;
    /*text-align: left;*/
  }
</style>
