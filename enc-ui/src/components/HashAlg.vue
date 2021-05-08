<template>
  <div class="app-container">
    <p>{{ inputAreaTitle }}}</p>
    <el-input
      type="textarea"
      :rows="7"
      placeholder="输入待摘要数据"
      v-model="inputArea"
      @input="changeInputValueAction()"
      clearable>
    </el-input>
    <p>结果：</p>
    <el-input
      type="textarea"
      :rows="7"
      placeholder="输出结果"
      v-model="outputArea">
    </el-input>
    <el-row>
      <el-button type="primary" size="small" @click="onclickAction('sha1')">SHA-1</el-button>
      <el-button type="primary" size="small" @click="onclickAction('sha3')">SHA-3</el-button>
      <el-button type="primary" size="small" @click="onclickAction('sha224')">SHA-224</el-button>
      <el-button type="primary" size="small" @click="onclickAction('sha256')">SHA-256</el-button>
      <el-button type="primary" size="small" @click="onclickAction('sha384')">SHA-384</el-button>
      <el-button type="primary" size="small" @click="onclickAction('sha512')">SHA-512</el-button>

      <el-button type="primary" size="small" @click="onclickAction('md2')">MD2</el-button>
      <el-button type="primary" size="small" @click="onclickAction('md4')">MD4</el-button>
      <el-button type="primary" size="small" @click="onclickAction('md5')">MD5</el-button>
      <el-button type="primary" size="small" @click="onclickAction('sm3')">SM3</el-button>
    </el-row>
  </div>
</template>

<script>
  import { version, hash } from "@/api/hash"
  import { formatInputValue } from "@/utils/string"

  export default {
    name: "HashAlg",
    data() {
      return {
        inputAreaTitle: '数据：长度0(0x0)',
        formatInputArea: '',
        inputArea: '',
        outputArea: ''
      }
    },
    methods: {
      onclickAction(type) {
        hash({
          algName: type,
          data: this.inputArea,
        }).then(response => {
          this.outputArea = response.data.showData;
        });
      },
      changeInputValueAction(){
        this.formatInputArea = formatInputValue(this.inputArea);
        this.inputAreaTitle  = '数据：长度' + this.formatInputArea.length / 2 + '(' + (this.formatInputArea.length / 2).toString(16) + ')'
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
