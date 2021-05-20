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

      <el-row :gutter="0">
        <el-form-item label="曲线选择：">
          <el-select v-model="secp_value" placeholder="请选择">
            <el-option
              v-for="item in secp_options"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
          <el-button type="primary" size="small" @click="sm2CalAction('genkey')">产生密钥对</el-button>
          <el-button type="primary" size="small" @click="sm2CalAction('calpub')">私钥计算公钥</el-button>
        </el-form-item>
      </el-row>
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
                v-model="inputData"
                @input="changeInputValueAction('inputData')"
                clearable>
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <el-form-item label="哈希算法：">
        <el-select v-model="hash_value" placeholder="请选择">
          <el-option
            v-for="item in hash_options"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" size="small" @click="sm2CalAction('sign')">签名</el-button>
        <el-button type="primary" size="small" @click="sm2CalAction('vsign')">验签名</el-button>
      </el-form-item>

    </el-form>
  </div>
</template>

<script>
    export default {
      data() {
        return {
          publicKeyX: '',
          publicKeyY: '',
          privateKey: '',
          inputData: '',
          inputID: '',
          // 曲线信息
          secp_options: [{
            value: 'X9_62_prime192v1',
            label: 'X9_62_prime192v1'
          }, {
            value: 'Secp_224r1',
            label: 'Secp_224r1'
          }, {
            value: 'X9_62_prime256v1',
            label: 'X9_62_prime256v1'
          }, {
            value: 'Secp_384r1',
            label: 'Secp_384r1'
          }, {
            value: 'Secp_521r1',
            label: 'Secp_521r1'
          }],
          secp_value: 'X9_62_prime256v1',
          // 签名哈希信息
          hash_options:[{
            value: 'sha-1',
            label: 'SHA-1'
          }, {
            value: 'sha-256',
            label: 'SHA-256'
          }, {
            value: 'md5',
            label: 'MD5'
          }],
          hash_value:'sha-1'
        }
      },
      methods: {
        changeInputValueAction(type) {
          console.log(type);
        },
        sm2CalAction(type) {
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
