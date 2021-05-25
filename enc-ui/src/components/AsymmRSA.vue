<template>
  <el-timeline :reverse="reverse">
    <el-timeline-item
      v-for="(activity, index) in activities"
      :key="index"
      :timestamp="activity.timestamp">
      {{activity.content}}
    </el-timeline-item>
  </el-timeline>
</template>

<script>
    export default {
      data() {
        return {
          reverse: true,
          activities: [{
            content: '创建 RSA 页面',
            timestamp: '2021-05-24'
          }]
        };
      }
    };
</script>

<style scoped>

</style>
