<template>
  <div class="app-container">
    <el-form :label-position="labelPosition" label-width="150px" id="formlabel">
      <el-form-item label="公钥矩阵：">
        <el-upload
          class="upload-demo"
          action="https://jsonplaceholder.typicode.com/posts/"
          :on-preview="handlePreview"
          :on-remove="handleRemove"
          :before-remove="beforeRemove"
          multiple
          :limit="1"
          :on-exceed="handleExceed"
          :file-list="fileList">
          <el-button size="small" type="primary">点击上传</el-button>
        </el-upload>
      </el-form-item>
      <el-form-item label="私钥矩阵：">
        <el-upload
          class="upload-demo"
          action="https://jsonplaceholder.typicode.com/posts/"
          :on-preview="handlePreview"
          :on-remove="handleRemove"
          :before-remove="beforeRemove"
          multiple
          :limit="1"
          :on-exceed="handleExceed"
          :file-list="fileList">
          <el-button size="small" type="primary">点击上传</el-button>
        </el-upload>
      </el-form-item>
      <el-form-item label="用户ID（长度 0）:">
        <el-col :span="16">
          <el-input
            type="textarea"
            v-model="userId">
          </el-input>
        </el-col>
      </el-form-item>
      <el-form-item label="映射算法：">
        <el-select v-model="hash_value" placeholder="请选择">
          <el-option
            v-for="item in hash_options"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="结果：">
        <el-col :span="16">
          <el-input
            type="textarea"
            :rows="6"
            v-model="outputData">
          </el-input>
        </el-col>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" size="small" @click="onclickAction('public')">计算公钥</el-button>
        <el-button type="primary" size="small" @click="onclickAction('private')">计算私钥</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        labelPosition: 'top',
        fileList: [],
        userId: '',
        outputData: '',
        hash_options:[{
            value: 'sha-1',
            label: 'SHA-1'
          }, {
            value: 'sha-256',
            label: 'SHA-256'
          }, {
            value: 'md5',
            label: 'MD5'
          }, {
            value: 'sm3',
            label: 'SM3'
          },
        ],
        hash_value:'sm3'
      }
    },
    methods: {
      onclickAction(type) {
        alert("计算" + type);
      },
      handleRemove(file, fileList) {
        console.log(file, fileList);
      },
      handlePreview(file) {
        console.log(file);
      },
      handleExceed(files, fileList) {
        this.$message.warning(`当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
      },
      beforeRemove(file, fileList) {
        return this.$confirm(`确定移除 ${ file.name }？`);
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
