<template>
  <div>
    <div>
      <van-nav-bar
        title="消息"
        :border="false"
      />
    </div>

    <Nav active="/msg"/>
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
