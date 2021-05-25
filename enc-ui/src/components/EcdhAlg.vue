<template>
  <el-form>
    <el-form-item label="曲线选择：">
      <el-select v-model="secp" placeholder="请选择">
        <el-option
          v-for="item in secp_options"
          :key="item.value"
          :label="item.label"
          :value="item.value">
        </el-option>
      </el-select>
    </el-form-item>
    <el-row :gutter="10">
      <el-col :span="10">
        <el-form-item label="[Step 1] Alice's private value (a):">
          <el-input
            type="textarea"
            :rows="2"
            v-model="a"
            clearable>
          </el-input>
        </el-form-item>
        <el-form-item label="[Step 3] Alice's public point (A = aG) (X,Y):">
          <el-input
            type="textarea"
            :rows="2"
            v-model="AX"
            clearable>
          </el-input>
          <el-input
            type="textarea"
            :rows="2"
            v-model="AY"
            clearable>
          </el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" size="small" @click="genKeyAction('A')">产生密钥对</el-button>
          <el-button type="primary" size="small" @click="calPubAction('A')">私钥计算公钥</el-button>
        </el-form-item>
        <el-form-item label="[Step 5] Alice's secret key (S = aB = abG) (X,Y):">
          <el-input
            type="textarea"
            :rows="2"
            v-model="SAX"
            clearable>
          </el-input>
          <el-input
            type="textarea"
            :rows="2"
            v-model="SAY"
            clearable>
          </el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" size="small" @click="calSecretKeyAction('A')">计算 SECRET KEY</el-button>
        </el-form-item>
      </el-col>
      <el-col :span="10">
        <el-form-item label="[Step 2] Bob's private value (b):">
          <el-input
            type="textarea"
            :rows="2"
            v-model="b"
            clearable>
          </el-input>
        </el-form-item>
        <el-form-item label="[Step 4] Bob's public point (B = bG) (X,Y):">
          <el-input
            type="textarea"
            :rows="2"
            v-model="BX"
            clearable>
          </el-input>
          <el-input
            type="textarea"
            :rows="2"
            v-model="BY"
            clearable>
          </el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" size="small" @click="genKeyAction('B')">产生密钥对</el-button>
          <el-button type="primary" size="small" @click="calPubAction('B')">私钥计算公钥</el-button>
        </el-form-item>
        <el-form-item label="[Step 6] Bob's secret key (S = bA = baG) (X,Y):">
          <el-input
            type="textarea"
            :rows="2"
            v-model="SBX"
            clearable>
          </el-input>
          <el-input
            type="textarea"
            :rows="2"
            v-model="SBY"
            clearable>
          </el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" size="small" @click="calSecretKeyAction('B')">计算 SECRET KEY</el-button>
        </el-form-item>
      </el-col>
    </el-row>
  </el-form>
</template>

<script>
  import { genKeyPair, calPubKey, calSecretKey} from "@/api/ecdh"
  export default {
      data(){
        return {
          a:  "",
          AX: "",
          AY: "",
          b:  "",
          BX: "",
          BY: "",
          SAX: "",
          SAY: "",
          SBX: "",
          SBY: "",
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
          }, {
            value: 'sm2p256v1',
            label: 'sm2p256v1'
          }],
          secp: 'sm2p256v1',
        }
      },
      methods: {
        genKeyAction(type){
          switch (type) {
            case "A":
              genKeyPair({
                secp: this.secp,
              }).then(response=>{
                this.a = response.data.pri;
                this.AX= response.data.px;
                this.AY= response.data.py;
              });
              break;
            case "B":
              genKeyPair({
                secp: this.secp,
              }).then(response=>{
                this.b = response.data.pri;
                this.BX= response.data.px;
                this.BY= response.data.py;
              });
              break;
            default:
              break;
          }
        },
        calPubAction(type) {
          switch (type) {
            case "A":
              calPubKey({
                secp: this.secp,
                pri:  this.a
              }).then(response=>{
                this.AX= response.data.px;
                this.AY= response.data.py;
              });
              break;
            case "B":
              calPubKey({
                secp: this.secp,
                pri:  this.b
              }).then(response=>{
                this.BX= response.data.px;
                this.BY= response.data.py;
              });
              break;
            default:
              break;
          }
        },
        calSecretKeyAction(type) {
          switch (type) {
            case "A":
              calSecretKey({
                secp:this.secp,
                pri: this.a,
                px:  this.BX,
                py:  this.BY,
              }).then(response=>{
                this.SAX = response.data.sx;
                this.SAY = response.data.sy;
              })
              break;
            case "B":
              calSecretKey({
                secp:this.secp,
                pri: this.b,
                px:  this.AX,
                py:  this.AY,
              }).then(response=>{
                this.SBX = response.data.sx;
                this.SBY = response.data.sy;
              })
              break;
            default:
              break;
          }
        }
      }
    }
</script>

<style scoped>

</style>
