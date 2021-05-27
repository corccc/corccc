<template>
 <div class="app-container">
   <el-card
     style="width: 50%; height: 50%;"
     class="box-card">
     <el-image
       style="width: 100%;"
       :src="welcomeImage">
       <div slot="placeholder" class="image-slot">
         加载中<span class="dot">...</span>
       </div>
     </el-image>
   </el-card>
<!--   <p>{this.currentDate}</p>-->
 </div>
<!--  <div class="app-container">-->
<!--    <el-row :gutter="10">-->
<!--      <el-col :span="8">-->
<!--        <el-form-item>-->
<!--          <p>你好呀1</p>-->
<!--        </el-form-item>-->
<!--      </el-col>-->
<!--      <el-col :span="8">-->
<!--        <el-form-item>-->
<!--          <p>你好呀2</p>-->
<!--        </el-form-item>-->
<!--      </el-col>-->
<!--    </el-row>-->
<!--  </div>-->
</template>

<script>
  import {imageUrl} from "@/api/hello"
  export default {
    data() {
      return {
        welcomeImage : "",
        currentDate:  "",
      }
    },
    created() {
      this.getImageUrl();
    },
    methods: {
      getCurrentDate() {
        var myDate = new Date();
        myDate.getFullYear();
        myDate.getMonth();
        this.currentDate = myDate.getDate();
      },
      getImageUrl() {
        imageUrl({
        }).then( response => {
          if (response.status == 200) {
            var array  = response.data.data;
            var data   = array[0];
            this.welcomeImage = data.url;
          } else {
            this.welcomeImage = require('@/assets/welcome.jpg');
          }
        });
      }
    }
  }
</script>

<style scoped>

</style>
