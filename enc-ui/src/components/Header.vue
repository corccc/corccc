<template>
  <el-row type="flex" justify="space-between" align="center">
<!--    style="background-color:red"-->
    <el-col :span="1" align="center">
      <el-image
        style="width: 50px; height: 50px; "
        :src="require('@/assets/logo.png')">
      </el-image>
    </el-col>
    <el-col :span="4">
      <b>ENCTOOLS</b>
    </el-col>
    <el-col :span="8">
    </el-col>
    <el-col :span="10" align="right">
      <b>Bug 反馈：konglingqi@i-wall.cn</b>
    </el-col>
  </el-row>
</template>

<script>
    export default {
      data() {
        return{
          url: '@/access',
        }
      }
    }
</script>

<style scoped>
  .b {
    align-items: center;
  }
  .el-col{
    line-height: 50px;
    height: 50px;
  }
</style>
