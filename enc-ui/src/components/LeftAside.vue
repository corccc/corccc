<!--<el-menu-->
<!--      default-active="0"-->
<template>
  <div class="app-container">
    <el-menu
      @select="handleSelect"
      align="left">
      <router-link to="/SymmetricAlg">
        <el-menu-item index="0">
          <el-image
            style="width: 25px; height: 25px; "
            :src="require('@/assets/0.png')">
          </el-image>
          对称加解密
        </el-menu-item>
      </router-link>

      <router-link to="/ASymmetricAlg">
        <el-menu-item index="1">
          <el-image
            style="width: 25px; height: 25px; "
            :src="require('@/assets/1.png')">
          </el-image>
          非对称加解密
        </el-menu-item>
      </router-link>

      <router-link to="/HashAlg">
        <el-menu-item index="2">
          <el-image
            style="width: 25px; height: 25px; "
            :src="require('@/assets/2.png')">
          </el-image>
          哈希算法
        </el-menu-item>
      </router-link>

      <router-link to="/CpkAlg">
        <el-menu-item index="3">
          <el-image
            style="width: 25px; height: 25px; "
            :src="require('@/assets/3.png')">
          </el-image>
          CPK 算法
        </el-menu-item>
      </router-link>

      <router-link to="/EcdhAlg">
        <el-menu-item index="4">
          <el-image
            style="width: 25px; height: 25px; "
            :src="require('@/assets/4.png')">
          </el-image>
          ECDH 算法
        </el-menu-item>
      </router-link>

<!--      <router-link to="/X509Parse">-->
<!--        <el-menu-item index="5">-->
<!--          <el-image-->
<!--            style="width: 25px; height: 25px; "-->
<!--            :src="require('@/assets/5.png')">-->
<!--          </el-image>-->
<!--          X509 解析-->
<!--        </el-menu-item>-->
<!--      </router-link>-->

<!--      <router-link to="/DataConvert">-->
<!--        <el-menu-item index="6">-->
<!--          <el-image-->
<!--            style="width: 25px; height: 25px; "-->
<!--            :src="require('@/assets/6.png')">-->
<!--          </el-image>-->
<!--          数据转换-->
<!--        </el-menu-item>-->
<!--      </router-link>-->

    </el-menu>
  </div>
</template>

<script>
    export default {
      name: "LeftAside",
      paths: ['SymmetricAlg','ASymmetricAlg','HashAlg','CpkAlg','EcdhAlg','X509Parse','DataConvert'],
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
  .el-menu {
    text-color: black;
    border-right:0!important;
  }
  .el-menu-item {
    text-color: black;
  }
  .router-link {
    text-color: black;
  }
  /*el-menu-item {*/
  /*  text-decoration-color: #42b983;*/
  /*  text-decoration:none;*/
  /*}*/
  .router-link {
    color: green;
  }
  /*el-menu-item.is-active {*/
  /*  background-color: #3370ff !important;*/
  /*  color: #fff;*/
  /*}*/
</style>
