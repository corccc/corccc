<template>
  <div class="app-container">
    <el-form :label-position="labelPosition" label-width="150px" id="formlabel">
      <el-form-item label="公钥矩阵：">
        <el-upload
          class="upload-pub"
          ref="upload_pub"
          :on-exceed="handleExceed"
          :before-upload="handleBefore"
          :on-success="handleSuccess"
          :on-change="handleChangePub"
          :on-remove="handleRemovePub"
          :limit="1"
          :file-list="pubList"
          :show-file-list="true"
          :auto-upload="false"
          :disabled="upload.isUploading"
          :action="upload.url_pub_matrix"
          :data="form">
          <el-button size="small" type="primary">点击上传</el-button>
        </el-upload>
      </el-form-item>
      <el-form-item label="私钥矩阵：">
        <el-upload
          class="upload-pri"
          ref="upload_pri"
          :on-exceed="handleExceed"
          :before-upload="handleBefore"
          :on-success="handleSuccess"
          :on-change="handleChangePri"
          :on-remove="handleRemovePri"
          :limit="1"
          :file-list="priList"
          :show-file-list="true"
          :auto-upload="false"
          :disabled="upload.isUploading"
          :action="upload.url_pri_matrix"
          :data="form">
          <el-button size="small" type="primary">点击上传</el-button>
        </el-upload>
      </el-form-item>
      <el-form-item :label="inputUIDTitle">
        <el-col :span="16">
          <el-input
            type="textarea"
            v-model="form.uid"
            @input="changeInputValueAction()">
          </el-input>
        </el-col>
      </el-form-item>
      <el-form-item label="映射算法：">
        <el-select v-model="form.hash" placeholder="请选择">
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
            :rows="7"
            v-model="outputData">
          </el-input>
        </el-col>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" size="small" :autofocus="true" @click="onclickAction('public')">计算公钥</el-button>
        <el-button type="primary" size="small" @click="onclickAction('private')">计算私钥</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
  import { version, baseURL, pubMatrix, priMatrix, calPub, calPri} from "@/api/cpk"
  import { formatInputValue, formatLengthValue } from "@/utils/string"
  export default {
    data() {
      return {
        labelPosition: 'top',
        outputData: '',
        pubList: [],
        priList: [],
        inputUIDTitle: '用户ID：',
        // 用户导入参数
        upload: {
          isUploading: false,
          url_pub_matrix: "http://localhost:28084" + "/cpk/pubMatrix",
          url_pri_matrix: "http://localhost:28084" + "/cpk/priMatrix",
        },
        form: {
          hash:'sha-1',
          uid: "3132333435363738"
        },
        hash_options:[{
            value: 'sha-1',
            label: 'SHA-1'
        }],
      }
    },
    methods: {
      changeInputValueAction() {
        this.inputUIDTitle  = '用户ID：长度' + formatLengthValue(this.form.uid);
      },
      onclickAction(type) {
        switch (type) {
          case "public":
            if (this.pubList.length == 0) {
              this.outputData = "请上传公钥矩阵";
            } else {
              this.outputData = "";
              this.$refs.upload_pub.submit();
            }
            break;
          case "private":
            if (this.priList.length == 0) {
              this.outputData = "请上传私钥矩阵";
            } else {
              this.outputData = "";
              this.$refs.upload_pri.submit();
            }
            break;
          default:
            break;
        }
      },
      handleExceed(files, fileList) {
        this.$message.warning(`文件已上传请先移除文件后，在进行上传操作`);
      },
      handleBefore(file) {
        const isLt2M = file.size / 1024 / 1024 < 1;
        if (!isLt2M) {
          this.$message.error('上传矩阵大小不能超过 1MB!');
        }
      },
      handleChangePub(files, fileList) {
        this.pubList = fileList;
      },
      handleChangePri(files, fileList) {
        this.priList = fileList;
      },
      handleRemovePub(files, fileList) {
        this.pubList = fileList;
      },
      handleRemovePri(files, fileList) {
        this.priList = fileList;
      },
      handleSuccess(response) {
        this.upload.isUploading = false;
        try {
          this.pubList[0].status = 'ready';
          this.priList[0].status = 'ready';
        } catch (e) {
          console.log(e.message);
        }
        if (response.code == 200) {
          this.outputData = response.showData;
        } else {
          this.outputData = "计算失败";
        }
      },
    }
  }
</script>

<style scoped>
  #formlabel>>>.el-form-item__label {
    /*font-size: 12px;*/
    line-height: 12px;
  }
</style>
