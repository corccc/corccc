<template>
  <div class="app-container">
    <p>数据：长度0(0x0)</p>
    <el-input
      type="textarea"
      :rows="7"
      placeholder="输入待摘要数据"
      v-model="inputarea">
    </el-input>
    <p>结果：</p>
    <el-input
      type="textarea"
      :rows="7"
      placeholder="输出结果"
      v-model="outputarea">
    </el-input>
    <el-row>
      <el-button type="primary" size="small" @click="onclickAction('sha1')">SHA-1</el-button>
      <el-button type="primary" size="small" @click="onclickAction('sha224')">SHA-224</el-button>
      <el-button type="primary" size="small" @click="onclickAction('sha256')">SHA-256</el-button>
      <el-button type="primary" size="small" @click="onclickAction('sha384')">SHA-384</el-button>
      <el-button type="primary" size="small" @click="onclickAction('sha512')">SHA-512</el-button>
    </el-row>
    <el-row>
      <el-button type="primary" size="small">MD5</el-button>
    </el-row>
    <el-row>
      <el-button type="primary" size="small">SM3</el-button>
    </el-row>
  </div>
</template>

<script>
  import { version, hash } from "@/api/hash"
  export default {
    name: "HashAlg",
    data() {
      return {
        inputarea: '',
        outputarea: ''
      }
    },
    methods: {
      onclickAction(type) {
        hash({
          algName: "sm3",
          data: "0x31,0x32,0x33,0x34,0x35"
        }).then(response => {
          console.log(response);
        });
        // version().then(res => {
        //   console.log(res)
        // })
      }
    },
  }
</script>

<style scoped>
  p {
    font-size: 14px;
    text-align: left;
    line-height: 14px;
  }
  el-row {
    margin-top: 20px;
    margin-bottom: 20px;
    display: flex;
    flex-wrap: wrap
  }
</style>
