<template>
  <div class="app-container">
    <el-form label-position="top" label-width="150px" id="formlabel">
      <el-form-item :label=inputAreaTitle >
        <el-col :span="16">
          <el-input
            type="textarea"
            :rows="7"
            placeholder="输入待摘要数据"
            v-model="inputArea"
            @input="changeInputValueAction()"
            clearable>
          </el-input>
        </el-col>
      </el-form-item>

      <el-form-item label="结果:">
        <el-col :span="16">
          <el-input
            type="textarea"
            :rows="7"
            placeholder="输出结果"
            v-model="outputArea">
          </el-input>
        </el-col>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" size="small" @click="onclickAction('sha1')">SHA-1</el-button>
        <el-button type="primary" size="small" @click="onclickAction('sha3')">SHA-3</el-button>
        <el-button type="primary" size="small" @click="onclickAction('sha224')">SHA-224</el-button>
        <el-button type="primary" size="small" @click="onclickAction('sha256')">SHA-256</el-button>
        <el-button type="primary" size="small" @click="onclickAction('sha384')">SHA-384</el-button>
        <el-button type="primary" size="small" @click="onclickAction('sha512')">SHA-512</el-button>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" size="small" @click="onclickAction('md2')">MD2</el-button>
        <el-button type="primary" size="small" @click="onclickAction('md4')">MD4</el-button>
        <el-button type="primary" size="small" @click="onclickAction('md5')">MD5</el-button>
        <el-button type="primary" size="small" @click="onclickAction('sm3')">SM3</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
  import { version, hash } from "@/api/hash"
  import { formatInputValue, formatLengthValue} from "@/utils/string"
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
        this.inputAreaTitle  = '数据：长度' + formatLengthValue(this.inputArea);
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
    /*display: flex;*/
    flex-wrap: wrap
  }
  #formlabel>>>.el-form-item__label {
    /*font-size: 12px;*/
    line-height: 12px;
  }
</style>
