<template>
  <div class="app-container">
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="SM2 算法" name="sm2">
        <AsymmSM2/>
      </el-tab-pane>
      <el-tab-pane label="ECC 算法" name="ecc">
        <AsymmECC/>
      </el-tab-pane>
      <el-tab-pane label="RSA 算法" name="rsa">
        <AsymmRSA/>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
    import AsymmSM2 from "./AsymmSM2";
    import AsymmECC from "./AsymmECC";
    import AsymmRSA from "./AsymmRSA";
    export default {
      components: {AsymmRSA, AsymmECC, AsymmSM2},
      data() {
        return {
          activeName: "sm2",
        }
      },
      methods: {
        handleClick(tab, event) {
          console.log(tab, event);
        }
      }
    }
</script>

<style scoped>
  p {
    font-size: 14px;
    text-align: left;
    line-height: 14px;
  }
</style>
