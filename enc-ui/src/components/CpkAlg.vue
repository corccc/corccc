<template>
  <div class="app-container">
<!--    <p>密文</p>-->
    <el-input
      type="textarea"
      :rows="2"
      placeholder="CPK"
      v-model="textarea">
    </el-input>
  </div>
</template>

<script>
    export default {
      name: "Cpk",
      data() {
        return {
          textarea: ''
        }
      }
    }
</script>

<style scoped>

</style>
