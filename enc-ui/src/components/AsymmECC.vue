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
      <el-row :gutter="0">
        <el-form-item label="曲线选择：">
          <el-select v-model="secp" placeholder="请选择">
            <el-option
              v-for="item in secp_options"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
          <el-button type="primary" size="small" @click="ecCalAction('genkey')">产生密钥对</el-button>
          <el-button type="primary" size="small" @click="ecCalAction('calpub')">私钥计算公钥</el-button>
        </el-form-item>
      </el-row>
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
      <el-form-item>
        <el-button type="primary" size="small" @click="ecCalAction('enc')">ECC 加密(ECIES)</el-button>
        <el-button type="primary" size="small" @click="ecCalAction('dec')">ECC 解密(ECIES)</el-button>
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

      <el-row :gutter="0">
        <el-form-item label="哈希选择：">
          <el-select v-model="hash" placeholder="请选择">
            <el-option
              v-for="item in hash_options"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
          <el-button type="primary" size="small" @click="ecCalAction('sign')">ECC 签名</el-button>
          <el-button type="primary" size="small" @click="ecCalAction('vsign')">ECC 验签</el-button>
        </el-form-item>
      </el-row>
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
        inputSignLabel: '签名数据: ',
        inputSign: '',
        // 曲线信息
        secp_options: [{
          value: 'secp128r1',
          label: 'secp128r1'
        }, {
          value: 'secp160k1',
          label: 'secp160k1'
        }, {
          value: 'secp160r1',
          label: 'secp160r1'
        }, {
          value: 'secp192k1',
          label: 'secp192k1'
        }, {
          value: 'secp192r1',
          label: 'secp192r1'
        }, {
          value: 'secp224k1',
          label: 'secp224k1'
        }, {
          value: 'secp224r1',
          label: 'secp224r1'
        }, {
          value: 'secp256r1',
          label: 'secp256r1'
        }],
        secp: 'secp256r1',
        // 哈希信息
        hash_options: [{
          value: 'SHA1withECDSA',
          label: 'SHA-1'
        },{
          value: 'SHA224withECDSA',
          label: 'SHA-224'
        },{
          value: 'SHA256withECDSA',
          label: 'SHA-256'
        },{
          value: 'SHA384withECDSA',
          label: 'SHA-384'
        },{
          value: 'SHA512withECDSA',
          label: 'SHA-512'
        }],
      hash: 'SHA256withECDSA',
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
          case "inputSign":
            this.inputSignLabel = '签名数据: ' + formatLengthValue(this.inputSign);
            break;
          default:
            break;
        }
      },
      ecCalAction(type) {
        switch (type) {
          case "genkey":
            genKeyPair({
              algName: "ecc",
              secp: this.secp
            }).then(response => {
              this.px = response.data.px;
              this.py = response.data.py;
              this.pri = response.data.pri;
            });
            break;
          case "calpub":
            calPubKey({
              algName: "ecc",
              secp: this.secp,
              pri: this.pri,
            }).then(response => {
              this.px = response.data.px;
              this.py = response.data.py;
            });
            break;
          case "enc":
            enc({
              algName: "ecc",
              secp: this.secp,
              px: this.px,
              py: this.py,
              data: this.inputData,
            }).then(response => {
              this.outputData = response.data.showData;
            });
            break;
          case "dec":
            dec({
              algName: "ecc",
              secp: this.secp,
              pri: this.pri,
              data: this.inputData,
            }).then(response => {
              this.outputData = response.data.showData;
            });
            break;
          case "sign":
            sign({
              algName: "ecc",
              secp: this.secp,
              pri:  this.pri,
              data: this.inputData,
              hash: this.hash,
            }).then(response => {
              this.outputData = response.data.showData;
            });
            break;
          case "vsign":
            vsign({
              algName: "ecc",
              secp: this.secp,
              px: this.px,
              py: this.py,
              data: this.inputData,
              sign: this.inputSign,
              hash: this.hash,
            }).then(response => {
              this.outputData = response.data.showData;
            });
            break;
          default:
            this.error("不支持的方法");
            break;
        }
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
