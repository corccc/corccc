<template>
  <el-row>
    <el-col :span="24" :top="0" :left="0">
      <el-menu class="el-menu-demo" mode="horizontal" @select="" :router="true">
        <el-menu-item index="/AsymmetricEccAlg"> ECC 算法</el-menu-item>
        <el-menu-item index="/AsymmetricRsaAlg"> RSA 算法</el-menu-item>
        <el-menu-item index="/AsymmetricSm2Alg"> SM2 算法</el-menu-item>
      </el-menu>
    </el-col>
  </el-row>
</template>

<script>
    export default {
        name: "Asymmetric"
    }
</script>

<style scoped>
  p {
    font-size: 14px;
    text-align: left;
    line-height: 14px;
  }
</style>
