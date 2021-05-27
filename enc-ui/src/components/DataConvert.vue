<template>
  <el-upload
    class="upload-demo"
    ref="upload"
    name="upload"
    :action="upload.url_pub_matrix"
    :on-preview="handlePreview"
    :on-remove="handleRemove"
    :file-list="fileList"
    :data="form"
    :auto-upload="false">
    <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
    <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload">上传到服务器</el-button>
    <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
  </el-upload>
</template>

<script>
    export default {
      data() {
        return {
          fileList: [],
          // 用户导入参数
          upload: {
            isUploading: false,
            url_pub_matrix: "http://localhost:28084" + "/cpk/pubMatrix",
            url_pri_matrix: "http://localhost:28084" + "/cpk/priMatrix",
          },
          form: {
            uid: "3132333435363738"
          },
        }
      },
      methods: {
        submitUpload() {
          this.$refs.upload.submit();
        },
        handleRemove(file, fileList) {
          console.log(file, fileList);
        },
        handlePreview(file) {
          console.log(file);
        },
      }
    }
</script>

<style scoped>

</style>
