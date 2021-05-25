<template>
  <div class="app-container">
    <el-form label-position="top" label-width="200px" id="formlabel">
      <el-form-item :label="pxLabel">
        <el-col :span="16">
          <el-input
            type="textarea"
            :rows="2"
            v-model="px"
            @input="changeInputValueAction('px')"
            clearable>
          </el-input>
        </el-col>
      </el-form-item>

      <el-form-item :label="pyLabel">
        <el-col :span="16">
          <el-input
            type="textarea"
            :rows="2"
            v-model="py"
            @input="changeInputValueAction('py')"
            clearable>
          </el-input>
        </el-col>
      </el-form-item>

      <el-form-item :label="priLabel">
        <el-col :span="16">
          <el-input
            type="textarea"
            :rows="2"
            v-model="pri"
            @input="changeInputValueAction('pri')"
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
            <el-form-item :label="inputDataLabel">
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
                clearable>
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <el-form-item :label="inputIDLabel">
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
        <el-button type="primary" size="small" @click="sm2CalAction('enc')">SM2 加密(C1C2C3)</el-button>
        <el-button type="primary" size="small" @click="sm2CalAction('dec')">SM2 解密(C1C2C3)</el-button>
      </el-form-item>
      <el-form-item :label="inputSignLabel">
        <el-input
          type="textarea"
          :rows="2"
          v-model="inputSign"
          @input="changeInputValueAction('inputSign')"
          clearable>
        </el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" size="small" @click="sm2CalAction('sign')">SM2 签名</el-button>
        <el-button type="primary" size="small" @click="sm2CalAction('vsign')">SM2 验签</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
  import { version, genKeyPair, calPubKey, enc, dec, sign, vsign} from "@/api/asymm"
  import { formatInputValue, formatLengthValue } from "@/utils/string"
  export default {
      data() {
        return {
          pxLabel: '公钥 x: ',
          px: '',
          pyLabel: '公钥 y: ',
          py: '',
          priLabel: '私钥: ',
          pri: '',
          inputDataLabel: '数据: ',
          inputData: '',
          outputData: '',
          inputIDLabel: 'ID 标识: ',
          inputID: '31323334353637383132333435363738',
          inputSignLabel: '签名数据: ',
          inputSign: '',
        }
      },
      methods: {
        changeInputValueAction(type) {
          switch (type) {
            case "px":
              this.pxLabel = '公钥 x: ' + formatLengthValue(this.px);
              break;
            case "py":
              this.pyLabel = '公钥 y: ' + formatLengthValue(this.py);
              break;
            case "pri":
              this.priLabel = '私钥: ' + formatLengthValue(this.pri);
              break;
            case "inputData":
              this.inputDataLabel = '数据: ' + formatLengthValue(this.inputData);
              break;
            case "inputID":
              this.inputIDLabel = 'ID 标识: ' + formatLengthValue(this.inputID);
              break;
            case "inputSign":
              this.inputSignLabel = '签名数据: ' + formatLengthValue(this.inputSign);
              break;
            default:
              break;
          }
            console.log(type);
        },
        sm2CalAction(type) {
          switch (type) {
              case "genkey":
                genKeyPair({
                  algName: "sm2",
                }).then(response => {
                  this.px = response.data.px;
                  this.py = response.data.py;
                  this.pri = response.data.pri;
                });
                break;
              case "calpub":
                calPubKey({
                  algName: "sm2",
                  pri: this.pri,
                }).then(response => {
                  this.px = response.data.px;
                  this.py = response.data.py;
                });
                break;
              case "enc":
                enc({
                  algName: "sm2",
                  px: this.px,
                  py: this.py,
                  data: this.inputData,
                }).then(response => {
                  this.outputData = response.data.showData;
                });
                break;
              case "dec":
                dec({
                  algName: "sm2",
                  pri: this.pri,
                  data: this.inputData,
                }).then(response => {
                  this.outputData = response.data.showData;
                });
                break;
              case "sign":
                sign({
                  algName: "sm2",
                  pri:  this.pri,
                  id:   this.inputID,
                  data: this.inputData,
                }).then(response => {
                  this.outputData = response.data.showData;
                });
                break;
              case "vsign":
                vsign({
                  algName: "sm2",
                  px: this.px,
                  py: this.py,
                  id: this.inputID,
                  data: this.inputData,
                  sign: this.inputSign,
                }).then(response => {
                  this.outputData = response.data.showData;
                });
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
