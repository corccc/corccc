<template>
  <div class="app-container">
    <el-form :label-position="labelPosition" label-width="150px" id="formlabel">
      <el-form-item :label=inputDataLabel>
        <el-col :span="16">
          <el-input
            type="textarea"
            :rows="6"
            placeholder="输入待加密数据"
            @input="changeInputValueAction('data')"
            v-model="inputData">
          </el-input>
        </el-col>
      </el-form-item>
      <el-form-item :label=inputKeyLabel>
        <el-col :span="16">
          <el-input
            type="textarea"
            :rows="1"
            placeholder="请输入密钥"
            @input="changeInputValueAction('key')"
            v-model="inputKey">
          </el-input>
        </el-col>
      </el-form-item>
      <el-form-item :label=inputIvLabel>
        <el-col :span="16">
          <el-input
            type="textarea"
            :rows="1"
            placeholder="请输入初始向量"
            @input="changeInputValueAction('iv')"
            v-model="inputIv">
          </el-input>
        </el-col>
      </el-form-item>
    </el-form>

    <el-form>
      <el-row>
        <el-col :span="8">
          <el-form-item label="加解密模式：">
            <el-select v-model="encValue" placeholder="请选择">
              <el-option
                v-for="item in enc_options"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="数据填充模式：">
            <el-select v-model="paddingValue" placeholder="请选择">
              <el-option
                v-for="item in padding_options"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>

    <el-form>
      <el-form-item>
        <el-col :span="16">
          <el-input
            type="textarea"
            :rows="6"
            placeholder="输出结果"
            v-model="outputData">
          </el-input>
        </el-col>
      </el-form-item>
    </el-form>

    <el-form>
      <el-form-item>
        <el-button type="primary" size="small" @click="encAction('DES')">DES 加密</el-button>
        <el-button type="primary" size="small" @click="decAction('DES')">DES 解密</el-button>
        <el-button type="primary" size="small" @click="encAction('DESede')">3DES 加密</el-button>
        <el-button type="primary" size="small" @click="decAction('DESede')">3DES 解密</el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" size="small" @click="encAction('AES')">ASE 128 加密</el-button>
        <el-button type="primary" size="small" @click="decAction('AES')">ASE 128 解密</el-button>
        <el-button type="primary" size="small" @click="encAction('AES')">ASE 192 加密</el-button>
        <el-button type="primary" size="small" @click="decAction('AES')">ASE 192 解密</el-button>
        <el-button type="primary" size="small" @click="encAction('AES')">ASE 256 加密</el-button>
        <el-button type="primary" size="small" @click="decAction('AES')">ASE 256 解密</el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" size="small" @click="encAction('SM4')">SM4 加密</el-button>
        <el-button type="primary" size="small" @click="decAction('SM4')">SM4 解密</el-button>
      </el-form-item>
    </el-form>
  </div>

</template>

<script>
    import { version, encrypt, decrypt} from "@/api/symm"
    import { formatInputValue, formatLengthValue} from "@/utils/string"
    export default {
      name: "Symmetric",
      data() {
        return {
          labelPosition: 'top',
          mode: '1',
          inputDataLabel: '数据：长度0',
          inputData: '',
          inputKeyLabel:  '密钥：长度0',
          inputKey: '',
          inputIvLabel:  '初始向量：长度0',
          inputIv: '',
          outputData: '',
          // 加解密模式
          enc_options: [{
            value: 'ECB',
            label: 'ECB 模式'
          }, {
            value: 'CBC',
            label: 'CBC 模式'
          }, {
            value: 'CFB',
            label: 'CFB 模式'
          }, {
            value: 'OFB',
            label: 'OFB 模式'
          }, {
            value: 'CTR',
            label: 'CTR 模式'
          }],
          encValue: 'ECB',

          // 数据填充模式
          padding_options: [{
            value: 'NoPadding',
            label: 'NoPadding'
          }, {
            value: 'PKCS5Padding',
            label: 'PKCS5Padding'
          }, {
            value: 'PKCS7Padding',
            label: 'PKCS7Padding'
          }, {
            value: 'ISO10126-2Padding',
            label: 'ISO10126-2Padding'
          }, {
            value: 'X9.23Padding',
            label: 'X9.23Padding'
          }, {
            value: 'ISO7816-4Padding',
            label: 'ISO7816-4Padding'
          }
          ],
          paddingValue: 'NoPadding'
        }

      },
      methods: {
        encAction(alg) {
          encrypt({
            algName: alg + "/" + this.encValue + "/" + this.paddingValue,
            key: this.inputKey,
            iv:  this.inputIv,
            data: this.inputData,
          }).then(response => {
            this.outputData = response.data.showData;
          });
        },
        decAction(alg) {
          decrypt({
            algName: alg + "/" + this.encValue + "/" + this.paddingValue,
            key: this.inputKey,
            iv:  this.inputIv,
            data: this.inputData,
          }).then(response => {
            this.outputData = response.data.showData;
          });
        },
        changeInputValueAction(type) {
          switch (type) {
            case 'data':
              this.inputDataLabel = '数据：长度' + formatLengthValue(this.inputData);
              break;
            case 'key':
              this.inputKeyLabel = '密钥：长度' + formatLengthValue(this.inputKey);
              break;
            case 'iv':
              this.inputIvLabel = '初始向量：长度' + formatLengthValue(this.inputIv);
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
