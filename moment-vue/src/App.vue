<template>
  <div id="app">
    <keep-alive>
      <router-view v-if="$route.meta.keepAlive"/>
    </keep-alive>
    <router-view v-if="!$route.meta.keepAlive"/>
  </div>
</template>

<script>

  export default {
    name: 'App',
    data() {
      return {
      }
    },
    mounted() {
      var device = this.deviceType().device;
      // if (device === 'windows pc' || device === 'mac' || device === 'linux pc') {
      //   window.location.href = "https://moment.bluer.top";
      // }
    },
    methods: {
      // 判断设备类型
      deviceType() {
        let agent = navigator.userAgent.toLowerCase();
        return {
          device: function () {
            if (/windows/.test(agent)) {
              return 'windows pc';
            } else if (/iphone|ipod/.test(agent) && /mobile/.test(agent)) {
              return 'iphone';
            } else if (/ipad/.test(agent) && /mobile/.test(agent)) {
              return 'ipad';
            } else if (/android/.test(agent) && /mobile/.test(agent)) {
              return 'android';
            } else if (/linux/.test(agent)) {
              return 'linux pc';
            } else if (/mac/.test(agent)) {
              return 'mac';
            } else {
              return 'other';
            }
          }(),
        };
      },
      // 获取设备宽度
      deviceWidth() {
        return window.innerWidth;
      },
    },
  }
</script>

<style>

</style>
