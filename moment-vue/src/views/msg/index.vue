<template>
  <div>
    <div>
      <van-nav-bar
        title="消息"
        :border="false"
      />
    </div>

    <div style="width: 95%;margin: 10px auto;">
      <el-card shadow="hover" style="text-align: center;font-weight: 500">
        消息模块暂未开发
      </el-card>
    </div>
  </div>
</template>

<script>
  import preventBack from 'vue-prevent-browser-back';

  export default {
    mixins: [preventBack],//注入
    name: "index"
  }
</script>

<style scoped>

</style>
