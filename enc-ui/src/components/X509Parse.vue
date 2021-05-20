<template>
  <el-form :label-position="labelPosition" label-width="150px" id="formlabel">
    <el-form-item :label=parseData> </el-form-item>
    <el-form-item label="数据：长度0">
      <el-col :span="16">
        <el-input
          type="textarea"
          :rows="6"
          placeholder="输入待加密数据"
          v-model="inputData">
        </el-input>
      </el-col>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" size="small" @click="parseClickAction('parse')">解析</el-button>
      <el-button type="info" size="small" @click="clearClickAction('clear')">清空</el-button>

    </el-form-item>
  </el-form>
</template>

<script>
    export default {
      name: "X509Parse",
      data() {
        return {
          labelPosition: 'top',
          parseData: '',
          inputData: '',
        }
      },
      methods: {
        parseClickAction(){
          alert("parse action")
        },
        clearClickAction(){
          alert("clear action")
        }
      }
    }
</script>

<style scoped>
  #formlabel>>>.el-form-item__label {
    /*font-size: 12px;*/
    line-height: 12px;
  }
</style>
