<template>
  <div class="app-container">
    <el-form label-position="top" label-width="200px" id="formlabel">
      <el-form-item label="公钥 x:">
        <el-col :span="16">
          <el-input
            type="textarea"
            :rows="2"
            v-model="publicKeyX"
            @input="changeInputValueAction('publicx')"
            clearable>
          </el-input>
        </el-col>
      </el-form-item>

      <el-form-item label="公钥 y:">
        <el-col :span="16">
          <el-input
            type="textarea"
            :rows="2"
            v-model="publicKeyY"
            @input="changeInputValueAction('publicy')"
            clearable>
          </el-input>
        </el-col>
      </el-form-item>

      <el-form-item label="私钥:">
        <el-col :span="16">
          <el-input
            type="textarea"
            :rows="2"
            v-model="privateKey"
            @input="changeInputValueAction('private')"
            clearable>
          </el-input>
        </el-col>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" size="small" @click="sm2CalAction('genkey')">产生密钥对</el-button>
        <el-button type="primary" size="small" @click="sm2CalAction('calpub')">私钥计算公钥</el-button>
      </el-form-item>
      <el-form>
        <el-row :gutter="10">
          <el-col :span="8">
            <el-form-item label="数据:">
              <el-input
                type="textarea"
                :rows="7"
                v-model="inputData"
                @input="changeInputValueAction('inputData')"
                clearable>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="结果:">
              <el-input
                type="textarea"
                :rows="7"
                v-model="outputData"
                @input="changeInputValueAction('outputData')"
                clearable>
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <el-form-item label="ID 标识：">
        <el-col :span="16">
          <el-input
            type="textarea"
            :rows="1"
            v-model="inputID"
            @input="changeInputValueAction('inputID')"
            clearable>
          </el-input>
        </el-col>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" size="small" @click="sm2CalAction('enc')">SM2 加密</el-button>
        <el-button type="primary" size="small" @click="sm2CalAction('dec')">SM2 解密</el-button>
      </el-form-item>
      <el-form-item>
        <el-radio v-model="radio" label="1">C1C2C3</el-radio>
        <el-radio v-model="radio" label="2">C1C3C2</el-radio>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" size="small" @click="sm2CalAction('sign')">SM2 签名</el-button>
        <el-button type="primary" size="small" @click="sm2CalAction('vsign')">SM2 验签</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
  import { version, genKeyPair, calPubKey } from "@/api/asymm"
  import { formatInputValue, formatLengthValue } from "@/utils/string"
  export default {
      data() {
        return {
          publicKeyX: '',
          publicKeyY: '',
          privateKey: '',
          inputData: '',
          outputData: '',
          inputID: '',
          radio: '1'
        }
      },
      methods: {
        changeInputValueAction(type) {
            console.log(type);
        },
        sm2CalAction(type) {
          switch (type) {
              case "genkey":
                genKeyPair({
                  algName: "sm2",
                }).then(response => {
                  this.publicKeyX = response.data.px;
                  this.publicKeyY = response.data.py;
                  this.privateKey = response.data.pri;
                });
                break;
              case "calpub":
                calPubKey({
                  algName: "sm2",
                  pri: this.privateKey,
                }).then(response => {
                  this.publicKeyX = response.data.px;
                  this.publicKeyY = response.data.py;
                });
                break;
              case "enc":
                break;
              case "dec":
                break;
              case "sign":
                break;
              case "vsign":
                break;
              default:
                this.error("不支持的方法");
                break;
          }
          console.log(type);
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
