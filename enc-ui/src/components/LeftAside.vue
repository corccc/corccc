<template>
  <el-menu
    default-active="1"
    @select="handleSelect"
    align="left">
    <el-menu-item index="0">
<!--      <i class="el-icon-menu"></i>-->
      <router-link to="/SymmetricAlg" >对称加解密</router-link>
    </el-menu-item>

    <el-menu-item index="1">
<!--      <i class="el-icon-menu"></i>-->
      <router-link to="/ASymmetricAlg" >非对称加解密</router-link>
    </el-menu-item>

    <el-menu-item index="2">
<!--      <i class="el-icon-menu"></i>-->
      <router-link to="/HashAlg" >哈希算法</router-link>
    </el-menu-item>

    <el-menu-item index="3">
<!--      <i class="el-icon-menu"></i>-->
      <router-link to="/CpkAlg" >Cpk算法</router-link>
    </el-menu-item>

    <el-menu-item index="4">
<!--      <i class="el-icon-menu"></i>-->
      <router-link to="/X509Parse" >X509证书解析</router-link>
    </el-menu-item>

    <el-menu-item index="5">
<!--      <i class="el-icon-menu"></i>-->
      <router-link to="/DataConvert" >数据转换</router-link>
    </el-menu-item>
  </el-menu>
</template>

<script>
    export default {
      name: "LeftAside",
      paths: ['SymmetricAlg','ASymmetricAlg','HashAlg','CpkAlg','X509Parse','DataConvert'],
      methods: {
        handleSelect(key, keyPath) {
          console.log(key)
          console.log(this.$router);
          // this.$router.push(path);
        }
      }
    }
</script>

<style scoped>
  a {
    text-decoration: none;
  }
  /*el-menu-item {*/
  /*  text-decoration-color: #42b983;*/
  /*}*/
  el-menu-item.is-active {
    background-color: #3370ff !important;
    color: #fff;
  }
  el-menu-item {
    background-color: #333333;
  }
</style>
