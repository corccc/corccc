<template>
  <el-container>
    <el-header> <Header/> </el-header>
    <el-container>
      <el-aside width="150px"> <LeftAside/> </el-aside>
      <el-main> <Main/></el-main>
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
  .el-header, .el-footer {
    background-color: #B3C0D1;
    color: #333;
    text-align: center;
    line-height: 60px;
  }
  .el-aside {
    background-color: #D3DCE6;
    color: #333;
    text-align: center;
    line-height: 500px;
  }
  .el-main {
    background-color: #E9EEF3;
    color: #333;
    text-align: left;
    /*line-height: 160px;*/
  }
  body > .el-container {
    margin-bottom: 40px;
  }
  .el-container:nth-child(5) .el-aside,
  .el-container:nth-child(6) .el-aside {
    line-height: 260px;
  }
  .el-container:nth-child(7) .el-aside {
    line-height: 320px;
  }
</style>
